package dev.be.feign.feign.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class DemoFeignErrorDecoder implements ErrorDecoder {
    // Feign 에서 error decode 와 관련 된 코드 지원
    private final ErrorDecoder errorDecoder = new Default();

    // decode 같은 경우는 각 컴포넌트마다 정의해놓은 error 코드나 상태값이 다를 수 있으므로 DemoFeignConfig 에 빈 선언
    @Override
    public Exception decode(String methodKey, Response response) {
        // response 에서 어떤 status 값이 나왔는지 확인 가능
        HttpStatus httpStatus = HttpStatus.resolve(response.status());

        // 내가 외부 API 와 연동시 핸들링을 해야 되면 여기에 정의
        if (httpStatus == HttpStatus.NOT_FOUND) {
            System.out.println("[DemoFeignErrorDecoder] Http status = " + httpStatus);
            throw new RuntimeException(String.format("[RuntimeException] Http Status is %s", httpStatus));
        }
        // 모든 것을 핸들링 할 수 없으니 default 로 핸들링 된 것을 사용
        return errorDecoder.decode(methodKey, response);
    }
}
