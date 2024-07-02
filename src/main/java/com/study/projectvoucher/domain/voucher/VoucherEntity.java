package com.study.projectvoucher.domain.voucher;


import com.study.projectvoucher.domain.BaseEntity;
import com.study.projectvoucher.domain.common.type.VoucherAmount;
import com.study.projectvoucher.domain.common.type.VoucherStatus;
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

    @Enumerated(EnumType.STRING)
    private VoucherAmount amount;


    protected VoucherEntity(){}

    public VoucherEntity(String code, VoucherStatus status, LocalDate validFrom, LocalDate validTo, VoucherAmount amount) {
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

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public VoucherAmount getAmount() {
        return amount;
    }


    public void changeStatusToDisable() {
        if (this.status.equals(VoucherStatus.PUBLISH)) {
            this.status = VoucherStatus.DISABLE;
        }else {
            throw new IllegalStateException("이미 사용된 상태의 상품권 입니다.");
        }
    }

    public void changeStatusToUse() {
        if (this.status.equals(VoucherStatus.PUBLISH)) {
            this.status = VoucherStatus.USE;
        } else if (this.status.equals(VoucherStatus.USE)) {
            throw new IllegalStateException("이미 사용된 상태의 상품권 입니다.");
        } else {
            throw new IllegalStateException("사용할 수 없는 상태의 상품권 입니다.");
        }
    }
}
