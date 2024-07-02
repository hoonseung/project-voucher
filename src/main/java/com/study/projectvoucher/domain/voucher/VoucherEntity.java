package com.study.projectvoucher.domain.voucher;


import com.study.projectvoucher.domain.BaseEntity;
import com.study.projectvoucher.domain.common.type.VoucherAmount;
import com.study.projectvoucher.domain.common.type.VoucherStatus;
import com.study.projectvoucher.domain.history.VoucherHistoryEntity;
import jakarta.persistence.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "voucher_id")
    private List<VoucherHistoryEntity> histories = new ArrayList<>();


    protected VoucherEntity(){}

    public VoucherEntity(String code, VoucherStatus status, LocalDate validFrom, LocalDate validTo, VoucherAmount amount,
                         VoucherHistoryEntity voucherHistory) {
        this.code = code;
        this.status = status;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.amount = amount;
        this.histories.add(voucherHistory);
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

    public List<VoucherHistoryEntity> getHistories() {
        return histories;
    }

    public void changeStatusToDisable(VoucherHistoryEntity voucherHistory) {
        if (this.status.equals(VoucherStatus.PUBLISH)) {
            this.status = VoucherStatus.DISABLE;
            this.histories.add(voucherHistory);
        }else {
            throw new IllegalStateException("이미 사용된 상태의 상품권 입니다.");
        }
    }

    public void changeStatusToUse(VoucherHistoryEntity voucherHistory) {
        if (this.status.equals(VoucherStatus.PUBLISH)) {
            this.status = VoucherStatus.USE;
            this.histories.add(voucherHistory);
        } else if (this.status.equals(VoucherStatus.USE)) {
            throw new IllegalStateException("이미 사용된 상태의 상품권 입니다.");
        } else {
            throw new IllegalStateException("사용할 수 없는 상태의 상품권 입니다.");
        }
    }
}
