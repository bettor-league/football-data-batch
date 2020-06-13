package com.bettorleague.batch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class HttpInterceptor implements ClientHttpRequestInterceptor {

    @Value("${football-data.tokens:#{null}}")
    private List<String> tokens;
    private int cursor = 0;

    public HttpInterceptor() {
        tokens = Optional.ofNullable(tokens).orElse(List.of());
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request,
                                        byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        if(tokens.size() == 0) throw new RuntimeException("No token provided");

        ClientHttpResponse response;

        do {
            HttpHeaders headers = request.getHeaders();
            headers.add("X-Auth-Token", getNextToken());
            response = execution.execute(request, body);
        } while (!response.getStatusCode().equals(HttpStatus.OK));

        return response;
    }

    private String getNextToken() {
        return tokens.get(cursor >= tokens.size() ? 0 : cursor++);
    }


}
