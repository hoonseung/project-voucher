package com.study.projectvoucher.model.voucher.v1;

import com.study.projectvoucher.domain.common.type.VoucherAmount;
import com.study.projectvoucher.domain.common.type.VoucherStatus;
import com.study.projectvoucher.domain.voucher.VoucherEntity;

import java.time.LocalDate;

public record VoucherPublishResponse(

        String code,

        VoucherStatus status,

        LocalDate validFrom,

        LocalDate validTo,

        VoucherAmount amount
) {


    public static VoucherPublishResponse from(VoucherEntity voucherEntity){
        return new VoucherPublishResponse(
                voucherEntity.getCode(),
                voucherEntity.getStatus(),
                voucherEntity.getValidFrom(),
                voucherEntity.getValidTo(),
                voucherEntity.getAmount()
        );
    }
}
