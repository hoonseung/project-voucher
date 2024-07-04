package com.study.projectvoucher.domain.contract;


import com.study.projectvoucher.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Table(name = "contract")
@Entity
public class ContractEntity extends BaseEntity {

    private String code;  // 게약 고유 코드

    private LocalDate validFrom; // 계약 유효 기간 시작일

    private LocalDate validTo; // 계약 유효 기간 만료일

    private Integer voucherValidPeriodDayCount; // 상품권 유효기간 일자


    protected ContractEntity(){}


    public ContractEntity(String code, LocalDate validFrom, LocalDate validTo, Integer voucherValidPeriodDayCount) {
        this.code = code;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.voucherValidPeriodDayCount = voucherValidPeriodDayCount;
    }

    public String getCode() {
        return code;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public Integer getVoucherValidPeriodDayCount() {
        return voucherValidPeriodDayCount;
    }


    public void periodIsValid(){
        if(this.validTo.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("유효기간이 지난 계약입니다.");
    }
}
