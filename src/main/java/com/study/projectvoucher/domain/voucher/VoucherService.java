package com.study.projectvoucher.domain.voucher;

import com.study.projectvoucher.common.type.VoucherStatus;
import com.study.projectvoucher.domain.contract.ContractEntity;
import com.study.projectvoucher.domain.contract.ContractRepository;
import com.study.projectvoucher.domain.history.VoucherHistoryEntity;
import com.study.projectvoucher.domain.validator.VoucherDisableValidator;
import com.study.projectvoucher.domain.validator.VoucherPublishValidator;
import com.study.projectvoucher.model.voucher.v2.*;
import com.study.projectvoucher.model.voucher.v3.VoucherPublishV3Request;
import com.study.projectvoucher.model.voucher.v3.VoucherPublishV3Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Transactional(readOnly = true)
@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final ContractRepository contractRepository;
    private final VoucherPublishValidator voucherPublishValidator;
    private final VoucherDisableValidator voucherDisableValidator;


    public VoucherService(VoucherRepository voucherRepository, ContractRepository contractRepository,
                          VoucherPublishValidator voucherPublishValidator, VoucherDisableValidator voucherDisableValidator) {
        this.voucherRepository = voucherRepository;
        this.contractRepository = contractRepository;
        this.voucherPublishValidator = voucherPublishValidator;
        this.voucherDisableValidator = voucherDisableValidator;
    }

    // 상품권 사용불가 처리
    @Transactional
    public VoucherDisableV2Response disableCodeV3(VoucherDisableV2Request disableV2Request) {
        final VoucherEntity voucherPs = voucherRepository.findByCode(disableV2Request.code())
                .orElseThrow(() -> new IllegalArgumentException("해당 상품권을 찾을 수 없습니다."));
        final String orderId = getUUID();
        voucherDisableValidator.validate(voucherPs, disableV2Request); // 발행자만 폐기 가능하도록 검증

        final VoucherHistoryEntity voucherHistory = new VoucherHistoryEntity(
                orderId, disableV2Request.requestType(), disableV2Request.requestId(), VoucherStatus.DISABLE, "상품권 폐기 테스트");

        voucherPs.changeStatusToDisable(voucherHistory);
        return new VoucherDisableV2Response(orderId);
    }


    // 상품권 사용
    @Transactional
    public VoucherUseV2Response useCodeV2(VoucherUseV2Request useV2Request){
        final VoucherEntity voucherPs = voucherRepository.findByCode(useV2Request.code())
                .orElseThrow(() -> new IllegalArgumentException("해당 상품권을 찾을 수 없습니다."));
        final String orderId = getUUID();
        final VoucherHistoryEntity voucherHistory = new VoucherHistoryEntity(
                orderId, useV2Request.requestType(), useV2Request.requestId(), VoucherStatus.USE, "상품권 사용 테스트");

        voucherPs.changeStatusToUse(voucherHistory);
        return new VoucherUseV2Response(orderId);
    }


    /**
     * V3
     */
    // 상품권 생성
    @Transactional
    public VoucherPublishV3Response publishV3(VoucherPublishV3Request publishV3Request){
        final String code = getUUID();
        final String orderId = getUUID();

        final ContractEntity contractEntity = contractRepository.findByCode(publishV3Request.contractCode())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계약입니다."));
        voucherPublishValidator.validate(contractEntity); // 유효기간 확인

        final VoucherHistoryEntity voucherHistoryEntity =
                new VoucherHistoryEntity(orderId, publishV3Request.requestType(), publishV3Request.requestId(), VoucherStatus.PUBLISH, "테스트 발행");
        final VoucherEntity voucherEntity = new VoucherEntity(code, VoucherStatus.PUBLISH,
                publishV3Request.amount(), voucherHistoryEntity, contractEntity);
        return VoucherPublishV3Response.from(voucherRepository.save(voucherEntity), orderId, publishV3Request.requestType(),
                publishV3Request.contractCode());
    }








    private static String getUUID() {
        return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
    }


}
