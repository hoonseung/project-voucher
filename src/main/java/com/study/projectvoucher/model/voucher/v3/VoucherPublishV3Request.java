package com.study.projectvoucher.model.voucher.v3;

import com.study.projectvoucher.common.type.RequestType;
import com.study.projectvoucher.common.type.VoucherAmount;

public record VoucherPublishV3Request(

        RequestType requestType,
        String requestId,
        String contractCode,
        VoucherAmount amount
) {
}
