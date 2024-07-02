package com.study.projectvoucher.service.v2;

import com.study.projectvoucher.domain.common.dto.RequestContext;
import com.study.projectvoucher.domain.common.type.RequestType;
import com.study.projectvoucher.domain.common.type.VoucherAmount;
import com.study.projectvoucher.domain.common.type.VoucherStatus;
import com.study.projectvoucher.domain.voucher.VoucherEntity;
import com.study.projectvoucher.domain.voucher.VoucherRepository;
import com.study.projectvoucher.domain.voucher.VoucherService;
import com.study.projectvoucher.model.voucher.v1.VoucherPublishResponse;
import com.study.projectvoucher.model.voucher.v2.VoucherPublishV2Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
 class VoucherServiceV2Tests {

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private VoucherRepository voucherRepository;

    @DisplayName("발행된 상품권은 code로 조회 가능해야한다.")
    @Test
    void whenPublishedThenEnableShouldSearchCode() {
        final LocalDate validFrom = LocalDate.now();
        final LocalDate validTo = LocalDate.now().plusDays(30);

        VoucherPublishV2Response v2Response = voucherService.publishV2(new RequestContext(
                RequestType.PARTNER, UUID.randomUUID().toString()), validFrom, validTo, VoucherAmount.KRW_30000);

      final VoucherEntity voucherPs = voucherRepository.findByCode(v2Response.code()).get();

      assertThat(voucherPs.getCode()).isEqualTo(v2Response.code());
        assertThat(voucherPs.getStatus()).isEqualTo(VoucherStatus.PUBLISH);
    }


   @DisplayName("발행된 상품권은 사용불가 처리 할 수 있다.")
   @Test
   void whenPublishedThenEnableShouldChangeToCancelVoucherStatus() {
      final LocalDate validFrom = LocalDate.now();
      final LocalDate validTo = LocalDate.now().plusDays(30);


       VoucherPublishV2Response v2Response = voucherService.publishV2(new RequestContext(
               RequestType.PARTNER, UUID.randomUUID().toString()), validFrom, validTo, VoucherAmount.KRW_30000);
      voucherService.disableCode(v2Response.code());
      final VoucherEntity voucherPs = voucherRepository.findByCode(v2Response.code()).get();

      assertThat(voucherPs.getCode()).isEqualTo(v2Response.code());
      assertThat(voucherPs.getStatus()).isEqualTo(VoucherStatus.DISABLE);
      assertThat(voucherPs.getCreatedAt()).isNotEqualTo(voucherPs.getUpdatedAt());
   }


    @DisplayName("발행된 상품권은 사용할 수 있다.")
    @Test
    void whenPublishedThenEnableShouldVoucher() {
        final LocalDate validFrom = LocalDate.now();
        final LocalDate validTo = LocalDate.now().plusDays(30);


        VoucherPublishV2Response v2Response = voucherService.publishV2(new RequestContext(
                RequestType.PARTNER, UUID.randomUUID().toString()), validFrom, validTo, VoucherAmount.KRW_30000);
        voucherService.useCode(v2Response.code());
        final VoucherEntity voucherPs = voucherRepository.findByCode(v2Response.code()).get();

        assertThat(voucherPs.getCode()).isEqualTo(v2Response.code());
        assertThat(voucherPs.getStatus()).isEqualTo(VoucherStatus.USE);
        assertThat(voucherPs.getCreatedAt()).isNotEqualTo(voucherPs.getUpdatedAt());
    }






}
