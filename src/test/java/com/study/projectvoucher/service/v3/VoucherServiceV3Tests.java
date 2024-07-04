package com.study.projectvoucher.service.v3;

import com.study.projectvoucher.common.type.RequestType;
import com.study.projectvoucher.common.type.VoucherAmount;
import com.study.projectvoucher.common.type.VoucherStatus;
import com.study.projectvoucher.domain.contract.ContractEntity;
import com.study.projectvoucher.domain.contract.ContractRepository;
import com.study.projectvoucher.domain.history.VoucherHistoryEntity;
import com.study.projectvoucher.domain.voucher.VoucherEntity;
import com.study.projectvoucher.domain.voucher.VoucherRepository;
import com.study.projectvoucher.domain.voucher.VoucherService;
import com.study.projectvoucher.model.voucher.v2.VoucherDisableV2Request;
import com.study.projectvoucher.model.voucher.v2.VoucherUseV2Request;
import com.study.projectvoucher.model.voucher.v3.VoucherPublishV3Request;
import com.study.projectvoucher.model.voucher.v3.VoucherPublishV3Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
 class VoucherServiceV3Tests {

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private ContractRepository contractRepository;


    @DisplayName("발행된 상품권은 계약정보의 voucherValidPeriodDayCount 만큼 유효기간을 가져야 한다.")
    @Test
    void whenPublishedThenEnableShouldSearchCode() {
        final String contractCode = "CT001";


        VoucherPublishV3Response v3Response = voucherService.publishV3(new VoucherPublishV3Request(
                RequestType.PARTNER, UUID.randomUUID().toString(), contractCode,  VoucherAmount.KRW_30000));

      final VoucherEntity voucherPs = voucherRepository.findByCode(v3Response.code()).get();
        assertThat(voucherPs.getCode()).isEqualTo(v3Response.code());
        assertThat(voucherPs.getStatus()).isEqualTo(VoucherStatus.PUBLISH);

        final ContractEntity contractPs = contractRepository.findByCode(contractCode).get();
        assertThat(voucherPs.getValidTo()).isEqualTo(voucherPs.getValidFrom().plusDays(contractPs.getVoucherValidPeriodDayCount()));

        VoucherHistoryEntity historyEntity = voucherPs.getHistories().get(0);
        assertThat(historyEntity.getOrderId()).isEqualTo(v3Response.orderId());
        assertThat(historyEntity.getRequestType()).isEqualTo(v3Response.requestType());
        assertThat(historyEntity.getDescription()).isEqualTo("테스트 발행");
    }


   @DisplayName("발행된 상품권은 사용불가 처리 할 수 있다.")
   @Test
   void whenPublishedThenEnableShouldChangeToCancelVoucherStatus() {
      final String contractCode = "CT001";
      final String requestId = UUID.randomUUID().toString();
       VoucherPublishV3Response v3Response = voucherService.publishV3(new VoucherPublishV3Request(
               RequestType.PARTNER, requestId, contractCode, VoucherAmount.KRW_30000));


       voucherService.disableCodeV3(new VoucherDisableV2Request(RequestType.PARTNER, requestId, v3Response.code()));
      final VoucherEntity voucherPs = voucherRepository.findByCode(v3Response.code()).get();


      assertThat(voucherPs.getCode()).isEqualTo(v3Response.code());
      assertThat(voucherPs.getStatus()).isEqualTo(VoucherStatus.DISABLE);
      assertThat(voucherPs.getCreatedAt()).isNotEqualTo(voucherPs.getUpdatedAt());

       VoucherHistoryEntity historyEntity = voucherPs.getHistories().get(1);
       assertThat(historyEntity.getRequestType()).isEqualTo(v3Response.requestType());
       assertThat(historyEntity.getDescription()).isEqualTo("상품권 폐기 테스트");
       assertThat(historyEntity.getStatus()).isEqualTo(VoucherStatus.DISABLE);
   }


    @DisplayName("발행된 상품권은 사용할 수 있다.")
    @Test
    void whenPublishedThenEnableShouldVoucher() {
        final String contractCode = "CT001";

        VoucherPublishV3Response v3Response = voucherService.publishV3(new VoucherPublishV3Request(
                RequestType.PARTNER, UUID.randomUUID().toString(), contractCode, VoucherAmount.KRW_30000));


        voucherService.useCodeV2(new VoucherUseV2Request(RequestType.PARTNER, UUID.randomUUID().toString(), v3Response.code()));
        final VoucherEntity voucherPs = voucherRepository.findByCode(v3Response.code()).get();

        assertThat(voucherPs.getCode()).isEqualTo(v3Response.code());
        assertThat(voucherPs.getStatus()).isEqualTo(VoucherStatus.USE);
        assertThat(voucherPs.getCreatedAt()).isNotEqualTo(voucherPs.getUpdatedAt());

        VoucherHistoryEntity historyEntity = voucherPs.getHistories().get(1);
        assertThat(historyEntity.getStatus()).isEqualTo(VoucherStatus.USE);
        assertThat(historyEntity.getDescription()).isEqualTo("상품권 사용 테스트");
    }


    @DisplayName("유효기간이 지난 계약으로 상품권 발행 불가")
    @Test
    void whenContractExpiredThenEShouldDisabledVoucher() {
        final String contractCode = "CT010";
        final VoucherPublishV3Request publishV3Request = new VoucherPublishV3Request(
                RequestType.PARTNER, UUID.randomUUID().toString(), contractCode, VoucherAmount.KRW_30000);


        assertThatThrownBy(() -> voucherService.publishV3(publishV3Request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효기간이 지난 계약입니다.");
    }


    @DisplayName("상품권은 발행 요청자만 사용 불가 처리 가능")
    @Test
    void VoucherDisableCanBeOnlyVoucherPublisher() {
        final String contractCode = "CT001";
        final VoucherPublishV3Request publishV3Request = new VoucherPublishV3Request(
                RequestType.PARTNER, UUID.randomUUID().toString(), contractCode, VoucherAmount.KRW_30000);
        final VoucherPublishV3Response publishV3Response = voucherService.publishV3(publishV3Request);

        final VoucherDisableV2Request disableV2Request = new VoucherDisableV2Request(RequestType.USER, UUID.randomUUID().toString(),
                publishV3Response.code());

        assertThatThrownBy(() -> voucherService.disableCodeV3(disableV2Request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("사용 불가 처리 권한이 없는 상품권 입니다.");
    }






}
