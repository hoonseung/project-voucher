package com.study.projectvoucher.web.controller;

import com.study.projectvoucher.common.dto.RequestContext;
import com.study.projectvoucher.common.type.VoucherAmount;
import com.study.projectvoucher.common.type.VoucherStatus;
import com.study.projectvoucher.domain.voucher.VoucherService;
import com.study.projectvoucher.model.voucher.v1.VoucherDisableRequest;
import com.study.projectvoucher.model.voucher.v1.VoucherPublishRequest;
import com.study.projectvoucher.model.voucher.v1.VoucherPublishResponse;
import com.study.projectvoucher.model.voucher.v1.VoucherUseRequest;
import com.study.projectvoucher.model.voucher.v2.*;
import com.study.projectvoucher.model.voucher.v3.VoucherPublishV3Request;
import com.study.projectvoucher.model.voucher.v3.VoucherPublishV3Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequestMapping("/api")
@RestController
public class VoucherController {

    private final VoucherService voucherService;

    // 생성자
    public VoucherController(VoucherService voucherService){
        this.voucherService = voucherService;
    }

    /**
     *   V1
     */

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/v1/voucher")
    public VoucherPublishResponse publish(@RequestBody VoucherPublishRequest publishRequest){
        return voucherService.publish(
                publishRequest.amount(), LocalDate.now(), LocalDate.now().plusDays(1830L));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/v1/voucher/use")
    public void use(@RequestBody VoucherUseRequest useRequest){
        voucherService.useCode(useRequest.code());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/v1/voucher/disable")
    public void disable(@RequestBody VoucherDisableRequest disableRequest){
        voucherService.disableCode(disableRequest.code());
    }


    /**
     * V2
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/v2/voucher")
    public VoucherPublishV2Response publishV2(@RequestBody RequestContext requestContext, VoucherAmount amount){
        return voucherService.publishV2(requestContext,
                LocalDate.now(), LocalDate.now().plusDays(1830L), amount);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/v2/voucher/use")
    public VoucherUseV2Response useV2(@RequestBody VoucherUseV2Request useV2Request){
        return voucherService.useCodeV2(useV2Request);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/v2/voucher/disable")
    public VoucherDisableV2Response disableV2(@RequestBody VoucherDisableV2Request disableV2Request){
        return voucherService.disableCodeV2(disableV2Request);
    }

    /**
     * V3
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/v3/voucher")
    public VoucherPublishV3Response publishV3(@RequestBody VoucherPublishV3Request publishV3Request){
        return voucherService.publishV3(publishV3Request);
    }



}
