package com.study.projectvoucher.model.voucher.v2;


import com.study.projectvoucher.domain.common.type.RequestType;

public record VoucherUseV2Request(

        RequestType requestType,

        String requestId,

        String code

) {
}
