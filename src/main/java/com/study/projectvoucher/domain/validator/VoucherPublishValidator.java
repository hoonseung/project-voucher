package com.study.projectvoucher.domain.validator;

import com.study.projectvoucher.domain.contract.ContractEntity;
import org.springframework.stereotype.Component;


@Component
public class VoucherPublishValidator {


    public void validate(ContractEntity contractEntity) {
        // 상품권 발행을 위한 계약 유효 기간 발행 확인
        상품권_발행을_위한_계약_유효기간이_만료되었는지_확인(contractEntity);
    }

    private static void 상품권_발행을_위한_계약_유효기간이_만료되었는지_확인(ContractEntity contractEntity) {
        if (contractEntity.periodIsExpired()){
            throw new IllegalArgumentException("유효기간이 지난 계약입니다.");
        }
    }
}
