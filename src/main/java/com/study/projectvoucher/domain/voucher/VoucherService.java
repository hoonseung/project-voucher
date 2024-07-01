package com.study.projectvoucher.domain.voucher;

import com.study.projectvoucher.domain.common.VoucherStatus;
import com.study.projectvoucher.domain.model.voucher.VoucherRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public String publish(VoucherRequest voucherRequest){
        final String code = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        final VoucherEntity voucherEntity = new VoucherEntity(code,
                VoucherStatus.PUBLISH, voucherRequest.validFrom(), voucherRequest.validTo(), voucherRequest.amount());

        return voucherRepository.save(voucherEntity).getCode();
    }

    // 상품권 사용불가 처리
    @Transactional
    public void disableCode(final String code){
        final VoucherEntity voucherPs = voucherRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품권을 찾을 수 없습니다."));
        voucherPs.changeStatusToDisable();
    }



    // 상품권 사용
}
