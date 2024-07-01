package com.study.projectvoucher.domain.model.voucher;

import java.time.LocalDate;

public record VoucherRequest(
        LocalDate validFrom,

        LocalDate validTo,

        Long amount
) {
}
