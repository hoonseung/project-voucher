package com.study.projectvoucher.model.voucher.v2;

import com.study.projectvoucher.common.type.RequestType;
import com.study.projectvoucher.common.type.VoucherAmount;

public record VoucherPublishV2Request(

        RequestType requestType,

        String requestId,

        VoucherAmount amount
) {
}
