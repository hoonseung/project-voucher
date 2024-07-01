package com.study.projectvoucher.service;

import com.study.projectvoucher.domain.common.VoucherStatus;
import com.study.projectvoucher.domain.model.voucher.VoucherRequest;
import com.study.projectvoucher.domain.voucher.VoucherEntity;
import com.study.projectvoucher.domain.voucher.VoucherRepository;
import com.study.projectvoucher.domain.voucher.VoucherService;
import org.assertj.core.api.Assertions;
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


    @Test
    void whenPublishedThenEnableShouldSearchCode(){
       final LocalDate validFrom = LocalDate.now();
       final LocalDate validTo = LocalDate.now().plusDays(30);
       final Long amount = 10000L;
       final VoucherRequest voucherRequest = new VoucherRequest(validFrom, validTo, amount);


       final String code = voucherService.publish(voucherRequest);

       final VoucherEntity voucherPs = voucherRepository.findByCode(code).get();

       assertThat(voucherPs.getCode()).isEqualTo(code);
       assertThat(voucherPs.getStatus()).isEqualTo(VoucherStatus.PUBLISH);
    }
}
