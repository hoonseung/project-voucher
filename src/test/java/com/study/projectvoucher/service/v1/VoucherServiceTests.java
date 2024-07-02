package com.study.projectvoucher.service.v1;

import com.study.projectvoucher.domain.common.type.VoucherAmount;
import com.study.projectvoucher.domain.common.type.VoucherStatus;
import com.study.projectvoucher.domain.voucher.VoucherEntity;
import com.study.projectvoucher.domain.voucher.VoucherRepository;
import com.study.projectvoucher.domain.voucher.VoucherService;
import com.study.projectvoucher.model.voucher.v1.VoucherPublishResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
 class VoucherServiceTests {

   @Autowired
   private VoucherService voucherService;

   @Autowired
   private VoucherRepository voucherRepository;

   @DisplayName("발행된 상품권은 code로 조회 가능해야한다.")
   @Test
   void whenPublishedThenEnableShouldSearchCode() {
      final LocalDate validFrom = LocalDate.now();
      final LocalDate validTo = LocalDate.now().plusDays(30);

       VoucherPublishResponse response = voucherService.publish(VoucherAmount.KRW_30000, validFrom, validTo);

      final VoucherEntity voucherPs = voucherRepository.findByCode(response.code()).get();

      assertThat(voucherPs.getCode()).isEqualTo(response.code());
      assertThat(voucherPs.getStatus()).isEqualTo(VoucherStatus.PUBLISH);
   }


   @DisplayName("발행된 상품권은 사용불가 처리 할 수 있다.")
   @Test
   void whenPublishedThenEnableShouldChangeToCancelVoucherStatus() {
      final LocalDate validFrom = LocalDate.now();
      final LocalDate validTo = LocalDate.now().plusDays(30);


      VoucherPublishResponse response = voucherService.publish(VoucherAmount.KRW_30000, validFrom, validTo);
      voucherService.disableCode(response.code());
      final VoucherEntity voucherPs = voucherRepository.findByCode(response.code()).get();

      assertThat(voucherPs.getCode()).isEqualTo(response.code());
      assertThat(voucherPs.getStatus()).isEqualTo(VoucherStatus.DISABLE);
      assertThat(voucherPs.getCreatedAt()).isNotEqualTo(voucherPs.getUpdatedAt());
   }


    @DisplayName("발행된 상품권은 사용할 수 있다.")
    @Test
    void whenPublishedThenEnableShouldVoucher() {
        final LocalDate validFrom = LocalDate.now();
        final LocalDate validTo = LocalDate.now().plusDays(30);


        VoucherPublishResponse response = voucherService.publish(VoucherAmount.KRW_30000, validFrom, validTo);
        voucherService.useCode(response.code());
        final VoucherEntity voucherPs = voucherRepository.findByCode(response.code()).get();

        assertThat(voucherPs.getCode()).isEqualTo(response.code());
        assertThat(voucherPs.getStatus()).isEqualTo(VoucherStatus.USE);
        assertThat(voucherPs.getCreatedAt()).isNotEqualTo(voucherPs.getUpdatedAt());
    }






}
