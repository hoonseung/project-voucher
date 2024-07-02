package com.study.projectvoucher.common.type;

public enum VoucherAmount {

    KRW_30000(30_000L),

    KRW_50000(50_000L),

    KRW_100000(100_000L),

    ;

    private final Long amount;

    VoucherAmount(Long amount) {
        this.amount = amount;
    }


    public Long getAmount() {
        return amount;
    }
}
