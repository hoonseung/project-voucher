package com.study.projectvoucher.model.voucher.v2;

import com.study.projectvoucher.common.type.RequestType;
import com.study.projectvoucher.common.type.VoucherAmount;
import com.study.projectvoucher.common.type.VoucherStatus;
import com.study.projectvoucher.domain.voucher.VoucherEntity;

import java.time.LocalDate;

public record VoucherPublishV2Response(


        String orderId,

        String code,

        RequestType requestType,

        VoucherStatus status,

        LocalDate validFrom,

        LocalDate validTo,

        VoucherAmount amount
) {

    public static VoucherPublishV2Response from(VoucherEntity voucherEntity, String orderId, RequestType requestType){
        return new VoucherPublishV2Response(
                orderId,
                voucherEntity.getCode(),
                requestType,
                voucherEntity.getStatus(),
                voucherEntity.getValidFrom(),
                voucherEntity.getValidTo(),
                voucherEntity.getAmount()
        );
    }
}
