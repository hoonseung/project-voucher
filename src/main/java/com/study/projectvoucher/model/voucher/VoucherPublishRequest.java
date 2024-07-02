package com.study.projectvoucher.model.voucher;

import com.study.projectvoucher.domain.common.VoucherAmount;

public record VoucherPublishRequest(

        VoucherAmount amount
) {
}
