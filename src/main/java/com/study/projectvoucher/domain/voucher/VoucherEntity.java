package com.study.projectvoucher.domain.voucher;


import com.study.projectvoucher.domain.BaseEntity;
import com.study.projectvoucher.domain.common.VoucherStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;


import java.time.LocalDate;

@Table(name = "voucher")
@Entity
public class VoucherEntity extends BaseEntity {

    private String code;

    @Enumerated(EnumType.STRING)
    private VoucherStatus status;

    private LocalDate validFrom;

    private LocalDate validTo;

    private Long amount;


    protected VoucherEntity(){}

    public VoucherEntity(String code, VoucherStatus status, LocalDate validFrom, LocalDate validTo, Long amount) {
        this.code = code;
        this.status = status;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public VoucherStatus getStatus() {
        return status;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public Long getAmount() {
        return amount;
    }


    public void changeStatusToDisable() {
        this.status = VoucherStatus.DISABLE;
    }
}
