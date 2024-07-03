package com.study.projectvoucher.model.voucher.v3;

import com.study.projectvoucher.common.type.RequestType;
import com.study.projectvoucher.common.type.VoucherAmount;
import com.study.projectvoucher.common.type.VoucherStatus;
import com.study.projectvoucher.domain.voucher.VoucherEntity;
import com.study.projectvoucher.model.voucher.v2.VoucherPublishV2Response;

import java.time.LocalDate;

public record VoucherPublishV3Response(


        String orderId,

        String code,

        String contractCode,

        RequestType requestType,

        VoucherStatus status,

        LocalDate validFrom,

        LocalDate validTo,

        VoucherAmount amount
) {

    public static VoucherPublishV3Response from(VoucherEntity voucherEntity, String orderId, RequestType requestType, String contractCode){
        return new VoucherPublishV3Response(
                orderId,
                voucherEntity.getCode(),
                contractCode,
                requestType,
                voucherEntity.getStatus(),
                voucherEntity.getValidFrom(),
                voucherEntity.getValidTo(),
                voucherEntity.getAmount()
        );
    }
}
