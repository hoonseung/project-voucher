package com.study.projectvoucher.model.voucher;

import com.study.projectvoucher.domain.common.VoucherAmount;
import com.study.projectvoucher.domain.common.VoucherStatus;
import com.study.projectvoucher.domain.voucher.VoucherEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
