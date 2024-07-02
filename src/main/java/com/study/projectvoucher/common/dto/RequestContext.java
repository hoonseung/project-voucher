package com.study.projectvoucher.common.dto;

import com.study.projectvoucher.common.type.RequestType;

public record RequestContext(
        RequestType requestType,

        String requestId
) {
}
