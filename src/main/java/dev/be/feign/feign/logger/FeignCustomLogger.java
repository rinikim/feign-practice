package dev.be.feign.feign.logger;

import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

import static feign.Util.*;


@RequiredArgsConstructor
public class FeignCustomLogger extends Logger {
    private static final int DEFAULT_SLOW_API_TIME = 3000;
    private static final String SLOW_API_NOTICE = "Slow API";


    // log를 어떤 형식으로 남길지 정해준다.
    @Override
    protected void log(String configKey, String format, Object... args) {
        System.out.println(String.format(methodTag(configKey), format, args));
    }

    // request 만 핸들링 할 수 있다.
    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        super.logRequest(configKey, logLevel, request);
        System.out.println("[request] : " + request);
    }

    // request, response 포함하여 다른 것까지 핸들링 할 수 있다.
    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response,
                                              long elapsedTime) throws IOException {
        String protocolVersion = resolveProtocolVersion(response.protocolVersion());
        String reason =
                response.reason() != null && logLevel.compareTo(Level.NONE) > 0 ? " " + response.reason()
                        : "";
        int status = response.status();
        log(configKey, "<--- %s %s%s (%sms)", protocolVersion, status, reason, elapsedTime);
        if (logLevel.ordinal() >= Level.HEADERS.ordinal()) {

            for (String field : response.headers().keySet()) {
                if (shouldLogResponseHeader(field)) {
                    for (String value : valuesOrEmpty(response.headers(), field)) {
                        log(configKey, "%s: %s", field, value);
                    }
                }
            }

            int bodyLength = 0;
            if (response.body() != null && !(status == 204 || status == 205)) {
                // HTTP 204 No Content "...response MUST NOT include a message-body"
                // HTTP 205 Reset Content "...response MUST NOT include an entity"
                if (logLevel.ordinal() >= Level.FULL.ordinal()) {
                    log(configKey, ""); // CRLF
                }
                byte[] bodyData = Util.toByteArray(response.body().asInputStream());
                bodyLength = bodyData.length;
                if (logLevel.ordinal() >= Level.FULL.ordinal() && bodyLength > 0) {
                    log(configKey, "%s", decodeOrDefault(bodyData, UTF_8, "Binary data"));
                }

                // 시간이 오래걸리면, Time out 이 발생할 수 있기 때문에 slow API 를 출력한다.
                if (elapsedTime > DEFAULT_SLOW_API_TIME) {
                    log(configKey, "[%s] elapsedTime : %s", SLOW_API_NOTICE, elapsedTime);
                }

                log(configKey, "<--- END HTTP (%s-byte body)", bodyLength);
                return response.toBuilder().body(bodyData).build();
            } else {
                log(configKey, "<--- END HTTP (%s-byte body)", bodyLength);
            }
        }
        return response;
    }
}
