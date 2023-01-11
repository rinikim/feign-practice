package dev.be.feign.feign.intercepter;

import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor(staticName = "of")
public class DemoFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        // get 요청일 경우
        if (Objects.equals(template.method(), Request.HttpMethod.GET.name())) {
           log.debug("[GET] [DemoFeignInterceptor] queries : " + template.queries());
           return;
        }

        // post 요청일 경우
        String encodedRequestBody = StringUtils.toEncodedString(template.body(), StandardCharsets.UTF_8);
        System.out.println("[POST] [DemoFeignInterceptor] requestBody : " + encodedRequestBody);
        log.debug("[POST] [DemoFeignInterceptor] requestBody : " + encodedRequestBody);


        // 추가적으로 본인이 필요한 로직을 추가

        // string -> json (objectMapper) 값 셋팅은 생략
        // post 요청일 경우 값을 바꿀 때 name 같은 경우에 특정한 값을 뒤에 붙여준다.
        String convertRequestBody = encodedRequestBody;
        template.body(convertRequestBody);
    }
}
