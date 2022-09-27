package ru.practicum.explore.client;


import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class BaseClient {

    protected final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    protected <T> ResponseEntity<Object> post(String path, T body) {
        return post(path, body);
    }

    protected List<ViewStats> get(String path, Map<String, Object> parameters) {
        ResponseEntity<List<ViewStats>> list = makeAndSendRequest(HttpMethod.GET, path, parameters, null);
        return list.getBody();
    }

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

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

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
