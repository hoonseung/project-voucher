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
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@ActiveProfiles("test")
@SpringBootTest
 class VoucherServiceDynamicV2Tests {

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

                    VoucherPublishV2Response v2Response = voucherService.publishV2(new RequestContext(
                            RequestType.PARTNER, UUID.randomUUID().toString()), validFrom, validTo, VoucherAmount.KRW_30000);
                    final VoucherEntity voucherPs = voucherRepository.findByCode(v2Response.code()).get();
                    codes.add(v2Response.code());
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


                    VoucherPublishV2Response v2Response = voucherService.publishV2(new RequestContext(
                            RequestType.PARTNER, UUID.randomUUID().toString()), validFrom, validTo, VoucherAmount.KRW_30000);
                    codes.add(v2Response.code());
                    voucherService.useCode(v2Response.code());
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
