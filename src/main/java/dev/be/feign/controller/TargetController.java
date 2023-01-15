package dev.be.feign.controller;

import dev.be.feign.common.dto.BaseRequestInfo;
import dev.be.feign.common.dto.BaseResponseInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/post")
    public BaseResponseInfo demoGet(@RequestHeader("CustomHeaderName") String header,
                                    @RequestBody BaseRequestInfo requestInfo) {
        return BaseResponseInfo.builder()
                .header(header)
                .name(requestInfo.getName())
                .age(requestInfo.getAge())
                .build();
    }

    @GetMapping("/error")
    public ResponseEntity<BaseResponseInfo> errorDecoder() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
