package com.study.projectvoucher.model.voucher.v1;

import com.study.projectvoucher.common.type.VoucherAmount;

public record VoucherPublishRequest(

        VoucherAmount amount
) {
}
