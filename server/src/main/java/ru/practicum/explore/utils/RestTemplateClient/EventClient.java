package ru.practicum.explore.utils.RestTemplateClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.List;
import java.util.Map;

/**
 * Клиент для отправки статистики по событиям
 */
@Component
public class EventClient extends BaseClient {

    @Autowired
    public EventClient(@Value("http://localhost:9090") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }

    public void sendHit(EndpointHit endpointHit) {
        postHit("/hit", endpointHit);
    }

    public List<ViewStats> getStats(String start, String end, String[] uris, boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", uris,
                "unique", unique
        );
        try {
            return get("/stats", parameters);
        } catch (ResourceAccessException e) {
            return List.of(new ViewStats("null", "null", 0));
        }
    }
}
