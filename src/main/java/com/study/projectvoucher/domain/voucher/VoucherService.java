package com.study.projectvoucher.domain.voucher;

import com.study.projectvoucher.common.dto.RequestContext;
import com.study.projectvoucher.common.type.VoucherAmount;
import com.study.projectvoucher.common.type.VoucherStatus;
import com.study.projectvoucher.domain.contract.ContractEntity;
import com.study.projectvoucher.domain.contract.ContractRepository;
import com.study.projectvoucher.domain.history.VoucherHistoryEntity;
import com.study.projectvoucher.model.voucher.v1.VoucherPublishResponse;
import com.study.projectvoucher.model.voucher.v2.*;
import com.study.projectvoucher.model.voucher.v3.VoucherPublishV3Request;
import com.study.projectvoucher.model.voucher.v3.VoucherPublishV3Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;


@Transactional(readOnly = true)
@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final ContractRepository contractRepository;

    public VoucherService(VoucherRepository voucherRepository, ContractRepository contractRepository) {
        this.voucherRepository = voucherRepository;
        this.contractRepository = contractRepository;
    }

    // 상품권 생성 v1 사용불가
    @Transactional
    public VoucherPublishResponse publish(final VoucherAmount amount, final LocalDate validFrom, final LocalDate validTo){
        final String code = getUUID();
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
        final String code = getUUID();
        final String orderId = getUUID();
        final VoucherHistoryEntity historyEntity = new VoucherHistoryEntity(orderId, requestContext.requestType(), VoucherStatus.PUBLISH, "테스트 발행");
        final VoucherEntity voucherEntity = new VoucherEntity(code,
                VoucherStatus.PUBLISH, validFrom, validTo, amount, historyEntity);

        return VoucherPublishV2Response.from(voucherRepository.save(voucherEntity), orderId,
                requestContext.requestType());
    }


    // 상품권 사용불가 처리
    @Transactional
    public VoucherDisableV2Response disableCodeV2(VoucherDisableV2Request disableV2Request) {
        final VoucherEntity voucherPs = voucherRepository.findByCode(disableV2Request.code())
                .orElseThrow(() -> new IllegalArgumentException("해당 상품권을 찾을 수 없습니다."));
        final String orderId = getUUID();
        final VoucherHistoryEntity voucherHistory = new VoucherHistoryEntity(
                orderId, disableV2Request.requestType(), VoucherStatus.DISABLE, "상품권 폐기 테스트");

        voucherPs.changeStatusToDisable(voucherHistory);
        return new VoucherDisableV2Response(orderId);
    }


    // 상품권 사용
    @Transactional
    public VoucherUseV2Response useCodeV2(VoucherUseV2Request useV2Request){
        final VoucherEntity voucherPs = voucherRepository.findByCode(useV2Request.code())
                .orElseThrow(() -> new IllegalArgumentException("해당 상품권을 찾을 수 없습니다."));
        final String orderId = getUUID();
        final VoucherHistoryEntity voucherHistory = new VoucherHistoryEntity(
                orderId, useV2Request.requestType(), VoucherStatus.USE, "상품권 사용 테스트");

        voucherPs.changeStatusToUse(voucherHistory);
        return new VoucherUseV2Response(orderId);
    }


    /**
     * V3
     */

    // 상품권 생성
    @Transactional
    public VoucherPublishV3Response publishV3(VoucherPublishV3Request publishV3Request){
        final String code = getUUID();
        final String orderId = getUUID();

        final ContractEntity contractEntity = contractRepository.findByCode(publishV3Request.contractCode()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계약입니다."));
        final VoucherHistoryEntity voucherHistoryEntity = new VoucherHistoryEntity(orderId, publishV3Request.requestType(), VoucherStatus.PUBLISH, "테스트 발행");
        final VoucherEntity voucherEntity = new VoucherEntity(code, VoucherStatus.PUBLISH,
                LocalDate.now(), LocalDate.now().plusDays(contractEntity.getVoucherValidPeriodDayCount()),
                publishV3Request.amount(), voucherHistoryEntity);
        return VoucherPublishV3Response.from(voucherRepository.save(voucherEntity), orderId, publishV3Request.requestType(),
                publishV3Request.contractCode());
    }








    private static String getUUID() {
        return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
    }


}
