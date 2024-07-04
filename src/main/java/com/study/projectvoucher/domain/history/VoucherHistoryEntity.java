package com.study.projectvoucher.domain.history;


import com.study.projectvoucher.domain.BaseEntity;
import com.study.projectvoucher.common.type.RequestType;
import com.study.projectvoucher.common.type.VoucherStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Table(name = "voucher_history")
@Entity
public class VoucherHistoryEntity extends BaseEntity {

    private String orderId;

    @Enumerated(EnumType.STRING)
    private RequestType requestType;

    private String requestId;

    @Enumerated(EnumType.STRING)
    private VoucherStatus status;

    private String description;



    protected VoucherHistoryEntity(){}

    public VoucherHistoryEntity(String orderId, RequestType requestType, String requestId, VoucherStatus status, String description) {
        this.orderId = orderId;
        this.requestType = requestType;
        this.requestId = requestId;
        this.status = status;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public VoucherStatus getStatus() {
        return status;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getRequestId() {
        return requestId;
    }
}
