package com.study.projectvoucher.domain.voucher;

import com.study.projectvoucher.domain.common.dto.RequestContext;
import com.study.projectvoucher.domain.common.type.RequestType;
import com.study.projectvoucher.domain.common.type.VoucherAmount;
import com.study.projectvoucher.domain.common.type.VoucherStatus;
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



    // 상품권 생성
    @Transactional
    public VoucherPublishResponse publish(final VoucherAmount amount, final LocalDate validFrom, final LocalDate validTo){
        final String code = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        final VoucherEntity voucherEntity = new VoucherEntity(code, VoucherStatus.PUBLISH, validFrom, validTo, amount);

        return VoucherPublishResponse.from(voucherRepository.save(voucherEntity));
    }


    // 상품권 사용불가 처리
    @Transactional
    public void disableCode(final String code){
        final VoucherEntity voucherPs = voucherRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품권을 찾을 수 없습니다."));
        voucherPs.changeStatusToDisable();
    }



    // 상품권 사용
    @Transactional
    public void useCode(final String code){
        final VoucherEntity voucherPs = voucherRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품권을 찾을 수 없습니다."));
        voucherPs.changeStatusToUse();
    }


    // 상품권 생성
    @Transactional
    public VoucherPublishV2Response publishV2(RequestContext requestContext,
                                              final LocalDate validFrom, final LocalDate validTo, VoucherAmount amount){
        final String code = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        final String orderId = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        final VoucherEntity voucherEntity = new VoucherEntity(code,
                VoucherStatus.PUBLISH, validFrom, validTo, amount);

        return VoucherPublishV2Response.from(voucherRepository.save(voucherEntity), orderId,
                requestContext.requestType());
    }


    // 상품권 사용불가 처리
    @Transactional
    public VoucherDisableV2Response disableCodeV2(RequestContext requestContext , final String code) {
        final VoucherEntity voucherPs = voucherRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품권을 찾을 수 없습니다."));
        voucherPs.changeStatusToDisable();
        final String orderId = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");

        return new VoucherDisableV2Response(orderId);
    }


    // 상품권 사용
    @Transactional
    public VoucherUseV2Response useCodeV2(RequestContext requestContext , final String code){
        final VoucherEntity voucherPs = voucherRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품권을 찾을 수 없습니다."));
        voucherPs.changeStatusToUse();
        final String orderId = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");

        return new VoucherUseV2Response(orderId);
    }

}
