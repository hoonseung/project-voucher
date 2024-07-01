package com.study.projectvoucher.model.voucher;

import com.study.projectvoucher.domain.common.VoucherAmount;

import java.time.LocalDate;

public record VoucherRequest(
        LocalDate validFrom,

        LocalDate validTo,

        VoucherAmount amount
) {
}
