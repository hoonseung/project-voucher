package com.study.projectvoucher.web.controller;

import com.study.projectvoucher.domain.voucher.VoucherService;
import com.study.projectvoucher.model.voucher.v2.*;
import com.study.projectvoucher.model.voucher.v3.VoucherPublishV3Request;
import com.study.projectvoucher.model.voucher.v3.VoucherPublishV3Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class VoucherController {

    private final VoucherService voucherService;

    // 생성자
    public VoucherController(VoucherService voucherService){
        this.voucherService = voucherService;
    }


    /**
     * V2, V3
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/v3/voucher")
    public VoucherPublishV3Response publishV3(@RequestBody VoucherPublishV3Request publishV3Request){
        return voucherService.publishV3(publishV3Request);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/v2/voucher/use")
    public VoucherUseV2Response useV2(@RequestBody VoucherUseV2Request useV2Request){
        return voucherService.useCodeV2(useV2Request);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/v2/voucher/disable")
    public VoucherDisableV2Response disableV2(@RequestBody VoucherDisableV2Request disableV2Request){
        return voucherService.disableCodeV3(disableV2Request);
    }





}
