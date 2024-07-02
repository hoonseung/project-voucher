package com.study.projectvoucher.model.voucher.v2;

import com.study.projectvoucher.domain.common.type.RequestType;
import com.study.projectvoucher.domain.common.type.VoucherAmount;

public record VoucherPublishV2Request(

        RequestType requestType,

        String requestId,

        VoucherAmount amount
) {
}
