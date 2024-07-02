package com.study.projectvoucher.model.voucher.v1;

import com.study.projectvoucher.domain.common.type.VoucherAmount;

public record VoucherPublishRequest(

        VoucherAmount amount
) {
}
