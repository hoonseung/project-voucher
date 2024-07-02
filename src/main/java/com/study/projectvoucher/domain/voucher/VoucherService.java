package com.study.projectvoucher.domain.voucher;

import com.study.projectvoucher.common.dto.RequestContext;
import com.study.projectvoucher.common.type.VoucherAmount;
import com.study.projectvoucher.common.type.VoucherStatus;
import com.study.projectvoucher.domain.history.VoucherHistoryEntity;
import com.study.projectvoucher.model.voucher.v1.VoucherPublishResponse;
import com.study.projectvoucher.model.voucher.v2.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;


@Transactional(readOnly = true)
@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }



    // 상품권 생성 v1 사용불가
    @Transactional
    public VoucherPublishResponse publish(final VoucherAmount amount, final LocalDate validFrom, final LocalDate validTo){
        final String code = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        final VoucherEntity voucherEntity = new VoucherEntity(code, VoucherStatus.PUBLISH, validFrom, validTo, amount, null);

        return VoucherPublishResponse.from(voucherRepository.save(voucherEntity));
    }

    // 상품권 사용불가 처리 v1 사용불가
    @Transactional
    public void disableCode(final String code){
        final VoucherEntity voucherPs = voucherRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품권을 찾을 수 없습니다."));
        voucherPs.changeStatusToDisable(null);
    }


    // 상품권 사용 v1 사용불가
    @Transactional
    public void useCode(final String code){
        final VoucherEntity voucherPs = voucherRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품권을 찾을 수 없습니다."));
        voucherPs.changeStatusToUse(null);
    }


    // 상품권 생성
    @Transactional
    public VoucherPublishV2Response publishV2(RequestContext requestContext,
                                              final LocalDate validFrom, final LocalDate validTo, VoucherAmount amount){
        final String code = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        final String orderId = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        final VoucherHistoryEntity historyEntity = new VoucherHistoryEntity(orderId, requestContext.requestType(), VoucherStatus.PUBLISH, "테스트 발행");
        final VoucherEntity voucherEntity = new VoucherEntity(code,
                VoucherStatus.PUBLISH, validFrom, validTo, amount, historyEntity);

        return VoucherPublishV2Response.from(voucherRepository.save(voucherEntity), orderId,
                requestContext.requestType());
    }


    // 상품권 사용불가 처리
    @Transactional
    public VoucherDisableV2Response disableCodeV2(RequestContext requestContext , final String code) {
        final VoucherEntity voucherPs = voucherRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품권을 찾을 수 없습니다."));
        final String orderId = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        final VoucherHistoryEntity voucherHistory = new VoucherHistoryEntity(
                orderId, requestContext.requestType(), VoucherStatus.DISABLE, "상품권 폐기 테스트");

        voucherPs.changeStatusToDisable(voucherHistory);
        return new VoucherDisableV2Response(orderId);
    }


    // 상품권 사용
    @Transactional
    public VoucherUseV2Response useCodeV2(RequestContext requestContext , final String code){
        final VoucherEntity voucherPs = voucherRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품권을 찾을 수 없습니다."));
        final String orderId = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        final VoucherHistoryEntity voucherHistory = new VoucherHistoryEntity(
                orderId, requestContext.requestType(), VoucherStatus.USE, "상품권 사용 테스트");

        voucherPs.changeStatusToUse(voucherHistory);
        return new VoucherUseV2Response(orderId);
    }

}
