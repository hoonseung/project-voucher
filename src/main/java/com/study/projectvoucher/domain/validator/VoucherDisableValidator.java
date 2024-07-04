package com.study.projectvoucher.domain.validator;

import com.study.projectvoucher.domain.voucher.VoucherEntity;
import com.study.projectvoucher.model.voucher.v2.VoucherDisableV2Request;
import org.springframework.stereotype.Component;

@Component
public class VoucherDisableValidator {


    public void validate(VoucherEntity voucherPs, VoucherDisableV2Request disableV2Request){
        // 발행자만 폐기 가능하도록 요청자 검증
        상품권_사용_불가_처리_권한이_있는지_확인(voucherPs, disableV2Request);
    }

    private static void 상품권_사용_불가_처리_권한이_있는지_확인(VoucherEntity voucherPs, VoucherDisableV2Request disableV2Request) {
        if (!voucherPs.publishHistory().getRequestType().equals(disableV2Request.requestType())
                || !voucherPs.publishHistory().getRequestId().equals(disableV2Request.requestId())){
            throw new IllegalArgumentException("사용 불가 처리 권한이 없는 상품권 입니다.");
        }
    }
}

