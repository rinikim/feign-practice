package dev.be.feign.feign.client;

import dev.be.feign.common.dto.BaseRequestInfo;
import dev.be.feign.common.dto.BaseResponseInfo;
import dev.be.feign.feign.config.DemoFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "demo-client",   // pk 같은 값
        url = "${feign.url.prefix}",    // 하드코딩보다는 properties 에서 불러온다.
        configuration = DemoFeignConfig.class
)
public interface DemoFeignClient {

    // 두 가지 파라미터를 받는 Feign Client
    @GetMapping("/get") // url/get 으로 요청 (해당 url은 현재 localhost:8080/target_server 이다.
    ResponseEntity<BaseResponseInfo> callGet(@RequestHeader("CustomHeaderName") String customHeader,
                                             @RequestParam("name") String name,
                                             @RequestParam("age") Long age);
}
