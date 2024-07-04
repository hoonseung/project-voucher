package com.study.projectvoucher.domain.validator;

import com.study.projectvoucher.domain.contract.ContractEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class VoucherPublishValidator {

    public void validate(ContractEntity contractEntity) {
        if (contractEntity.periodIsExpired()){
            throw new IllegalArgumentException("유효기간이 지난 계약입니다.");
        }
    }
}
