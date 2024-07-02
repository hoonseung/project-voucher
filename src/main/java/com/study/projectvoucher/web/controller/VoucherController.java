package com.study.projectvoucher.web.controller;

import com.study.projectvoucher.common.dto.RequestContext;
import com.study.projectvoucher.domain.voucher.VoucherService;
import com.study.projectvoucher.model.voucher.v1.VoucherDisableRequest;
import com.study.projectvoucher.model.voucher.v1.VoucherPublishRequest;
import com.study.projectvoucher.model.voucher.v1.VoucherPublishResponse;
import com.study.projectvoucher.model.voucher.v1.VoucherUseRequest;
import com.study.projectvoucher.model.voucher.v2.*;
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
    public VoucherPublishV2Response publish(@RequestBody VoucherPublishV2Request publishV2Request){
        return voucherService.publishV2(new RequestContext(publishV2Request.requestType(), publishV2Request.requestId()),
                LocalDate.now(), LocalDate.now().plusDays(1830L), publishV2Request.amount());
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/v2/voucher/use")
    public VoucherUseV2Response use(@RequestBody VoucherUseV2Request useV2Request){
        return voucherService.useCodeV2(new RequestContext(useV2Request.requestType(),
                useV2Request.requestId()), useV2Request.code());
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/v2/voucher/disable")
    public VoucherDisableV2Response disable(@RequestBody VoucherDisableV2Request disableV2Request){
        return voucherService.disableCodeV2(new RequestContext(disableV2Request.requestType(),
                disableV2Request.requestId()), disableV2Request.code());
    }

}
