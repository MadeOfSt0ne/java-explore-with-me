package ru.practicum.explore.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explore.client.BaseClient;
import ru.practicum.stats.record.dto.EndpointHit;
import ru.practicum.stats.record.dto.ViewStats;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class EventClient extends BaseClient {

    @Autowired
    public EventClient(@Value("http://localhost:8080") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }

    public void makeAndSendEndpointHit(String app, HttpServletRequest request) {
        sendHit(new EndpointHit(app, request.getRequestURI(), request.getRemoteAddr()));
    }

    public ResponseEntity<Object> sendHit(EndpointHit endpointHit) {
        return post("/hit", endpointHit);
    }

    public List<ViewStats> getStats(String start, String end, String[] uris, boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", uris,
                "unique", unique
        );
        return get("/stats", parameters);
    }
}
