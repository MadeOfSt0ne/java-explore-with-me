package ru.practicum.explore.utils.RestTemplateClient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Класс, обрабатывающий статистику и предоставляющий нужную информацию
 */
@Service
@RequiredArgsConstructor
public class ViewsProcessor {

    private final EventClient eventClient;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Метод отправляет запрос в сервис статистики и возвращает количество просмотров
     *
     * @param eventId id события
     */
    public int getViews(long eventId) {
        String[] uri = new String[]{"/events/" + eventId};
        List<ViewStats> list = eventClient.getStats(LocalDateTime.now().minusYears(1).format(FORMATTER),
                LocalDateTime.now().format(FORMATTER), uri, false);
        return list.get(0).getHits();
    }
}