package com.study.projectvoucher.domain.voucher;

import com.study.projectvoucher.domain.common.VoucherAmount;
import com.study.projectvoucher.domain.common.VoucherStatus;
import com.study.projectvoucher.model.voucher.VoucherPublishRequest;
import com.study.projectvoucher.model.voucher.VoucherPublishResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;


@Transactional(readOnly = true)
@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }



    // 상품권 생성
    @Transactional
    public VoucherPublishResponse publish(final VoucherAmount amount, final LocalDate validFrom, final LocalDate validTo){
        final String code = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        final VoucherEntity voucherEntity = new VoucherEntity(code,
                VoucherStatus.PUBLISH, validFrom, validTo, amount);

        return VoucherPublishResponse.from(voucherRepository.save(voucherEntity));
    }

    // 상품권 사용불가 처리
    @Transactional
    public void disableCode(final String code){
        final VoucherEntity voucherPs = voucherRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품권을 찾을 수 없습니다."));
        voucherPs.changeStatusToDisable();
    }



    // 상품권 사용
    @Transactional
    public void useCode(final String code){
        final VoucherEntity voucherPs = voucherRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품권을 찾을 수 없습니다."));
        voucherPs.changeStatusToUse();
    }

}
