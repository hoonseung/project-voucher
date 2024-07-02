package com.study.projectvoucher.web.controller;

import com.study.projectvoucher.domain.voucher.VoucherService;
import com.study.projectvoucher.model.voucher.VoucherDisableRequest;
import com.study.projectvoucher.model.voucher.VoucherPublishRequest;
import com.study.projectvoucher.model.voucher.VoucherPublishResponse;
import com.study.projectvoucher.model.voucher.VoucherUseRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequestMapping("/api/v1/voucher")
@RestController
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService){
        this.voucherService = voucherService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public VoucherPublishResponse publish(@RequestBody VoucherPublishRequest publishRequest){
        return voucherService.publish(
                publishRequest.amount(), LocalDate.now(), LocalDate.now().plusDays(1830L));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/use")
    public void use(@RequestBody VoucherUseRequest useRequest){
        voucherService.useCode(useRequest.code());
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("disable")
    public void disable(@RequestBody VoucherDisableRequest disableRequest){
        voucherService.disableCode(disableRequest.code());
    }




}
