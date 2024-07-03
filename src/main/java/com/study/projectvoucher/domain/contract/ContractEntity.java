package com.study.projectvoucher.domain.contract;


import com.study.projectvoucher.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Table(name = "contract")
@Entity
public class ContractEntity extends BaseEntity {

    private String code;  // 게약 고유 코드

    private LocalDate validTo; // 계약 유효 기간 시작일

    private LocalDate validFrom; // 계약 유효 기간 만료일

    private Integer voucherValidPeriodDayCount; // 상품권 유효기간 일자


    protected ContractEntity(){}


    public ContractEntity(String code, LocalDate validTo, LocalDate validFrom, Integer voucherValidPeriodDayCount) {
        this.code = code;
        this.validTo = validTo;
        this.validFrom = validFrom;
        this.voucherValidPeriodDayCount = voucherValidPeriodDayCount;
    }

    public String getCode() {
        return code;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public Integer getVoucherValidPeriodDayCount() {
        return voucherValidPeriodDayCount;
    }
}
