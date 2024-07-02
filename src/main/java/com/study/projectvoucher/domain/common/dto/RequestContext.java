package com.study.projectvoucher.domain.common.dto;

import com.study.projectvoucher.domain.common.type.RequestType;

public record RequestContext(
        RequestType requestType,

        String requestId
) {
}
