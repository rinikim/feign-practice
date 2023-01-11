package dev.be.feign.controller;

import dev.be.feign.common.dto.BaseResponseInfo;
import org.springframework.web.bind.annotation.*;

/**
 * Feign Client 실습 테스트
 * localhost:8080/target_server/get 으로 요청을 보내면 여기로 요청이 온다.
 */
@RestController
@RequestMapping("/target_server")
public class TargetController {

    @GetMapping("/get")
    public BaseResponseInfo demoGet(@RequestHeader("CustomHeaderName") String header,
                                                   @RequestParam("name") String name,
                                                   @RequestParam("age") Long age) {
        return BaseResponseInfo.builder()
                .header(header)
                .name(name)
                .age(age)
                .build();
    }
}
