package ru.practicum.explore.utils.RestTemplateClient;


import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.springframework.http.RequestEntity.post;

/**
 * Клиент используется для отправки запросов и получения ответов с помощью RestTemplate
 */
public class BaseClient {

    protected final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    /**
     * Отправка запроса post
     */
    protected <T> void postHit(String path, T body) {
        post(path, body);
    }

    /**
     * Отправка запроса get
     */
    protected List<ViewStats> get(String path, Map<String, Object> parameters) {
        ResponseEntity<List<ViewStats>> list = makeAndSendRequest(HttpMethod.GET, path, parameters, null);
        return list.getBody();
    }

    /**
     * Формирование и отправка запроса
     */
    private <T> ResponseEntity<List<ViewStats>> makeAndSendRequest(HttpMethod method, String path,
                                                                   Map<String, Object> params, @Nullable T body) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders());
        ResponseEntity<List<ViewStats>> statsServerResponse;
        try {
            statsServerResponse = rest.exchange(path, method, requestEntity,
                    new ParameterizedTypeReference<>() {}, params);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(List.of(new ViewStats("null", "null", 0)));
        }
        return prepareGatewayResponse(statsServerResponse);
    }

    /**
     * Формирование хедеров
     */
    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    /**
     * Обработка ответа
     */
    private static ResponseEntity<List<ViewStats>> prepareGatewayResponse(ResponseEntity<List<ViewStats>> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());
        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }
        return responseBuilder.build();
    }
}
