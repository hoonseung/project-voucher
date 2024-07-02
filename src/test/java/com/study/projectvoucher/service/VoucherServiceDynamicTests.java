package com.study.projectvoucher.service;


import com.study.projectvoucher.domain.common.VoucherAmount;
import com.study.projectvoucher.domain.common.VoucherStatus;
import com.study.projectvoucher.model.voucher.VoucherPublishRequest;
import com.study.projectvoucher.domain.voucher.VoucherEntity;
import com.study.projectvoucher.domain.voucher.VoucherRepository;
import com.study.projectvoucher.domain.voucher.VoucherService;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.DynamicTest.*;

@ActiveProfiles("test")
@SpringBootTest
 class VoucherServiceDynamicTests {

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private VoucherRepository voucherRepository;


    @TestFactory
    Stream<DynamicTest> tests(){
        final List<String> codes = new ArrayList<>();

        return Stream.of(
                dynamicTest("[0] 상품권을 발행합니다.", () -> {
                    final LocalDate validFrom = LocalDate.now();
                    final LocalDate validTo = LocalDate.now().plusDays(30);
                    final VoucherPublishRequest voucherPublishRequest = new VoucherPublishRequest(validFrom, validTo, VoucherAmount.KRW_30000);


                    final String code = voucherService.publish(voucherPublishRequest);
                    final VoucherEntity voucherPs = voucherRepository.findByCode(code).get();
                    codes.add(code);
                    assertThat(voucherPs.getStatus()).isEqualTo(VoucherStatus.PUBLISH);

                }),
                dynamicTest("[0] 상품권을 사용 불가 처리합니다.", () -> {
                    final String code = codes.get(0);
                    voucherService.disableCode(code);
                    final VoucherEntity voucherPs = voucherRepository.findByCode(code).get();
                    assertThat(voucherPs.getStatus()).isEqualTo(VoucherStatus.DISABLE);
                }),

                dynamicTest("[0] 사용 불가 상태의 상품권은 사용할 수 없습니다.", () -> {
                    final String code = codes.get(0);
                    assertThatThrownBy( () -> voucherService.useCode(code)).isInstanceOf(IllegalStateException.class);
                }),

                dynamicTest("[1] 상품권을 사용합니다.", () -> {
                    final LocalDate validFrom = LocalDate.now();
                    final LocalDate validTo = LocalDate.now().plusDays(30);
                    final VoucherPublishRequest voucherPublishRequest = new VoucherPublishRequest(validFrom, validTo, VoucherAmount.KRW_30000);


                    final String code = voucherService.publish(voucherPublishRequest);
                    codes.add(code);
                    voucherService.useCode(code);
                }),

                dynamicTest("[1] 사용한 상품권은 사용 불가 처리 할 수 없습니다.", () -> {
                    final String code = codes.get(1);
                    final VoucherEntity voucherPs = voucherRepository.findByCode(code).get();
                    assertThatThrownBy(  () -> voucherService.disableCode(code)).isInstanceOf(IllegalStateException.class);
                    assertThat(voucherPs.getStatus()).isEqualTo(VoucherStatus.USE);
                }),

                dynamicTest("[1] 사용한 상품권은 또 사용할 수 없습니다.", () -> {
                    final String code = codes.get(1);
                    final VoucherEntity voucherPs = voucherRepository.findByCode(code).get();
                    assertThatThrownBy( () -> voucherService.useCode(code)).isInstanceOf(IllegalStateException.class);
                    assertThat(voucherPs.getStatus()).isEqualTo(VoucherStatus.USE);
                })



        );
    }
}
