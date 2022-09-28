package ru.practicum.explore.models.event.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * Объект, одновременно являющийся маппером, для передачи параметров запроса от публичного контроллера к сервису
 */
@Data
@NoArgsConstructor
public class PublicEventsRequest {
    /**
     * Текст поискового запроса
     */
    private String text;
    /**
     * Список идентификаторов категорий, среди которых нужно вести поиск
     */
    private List<Integer> categories;
    /**
     * Нужно ли искать только платные события
     */
    private boolean paid;
    /**
     * Дата и время не раньше которых должно произойти событие
     */
    private LocalDateTime rangeStart;
    /**
     * Дата и время не позже которых должно произойти событие
     */
    private LocalDateTime rangeEnd;
    /**
     * Нужно ли искать только события у которых не исчерпан лимит запросов на участие
     */
    private boolean onlyAvailable;
    /**
     * Вариант сортировки: по дате события или по количеству просмотров (EVENT_DATE, VIEWS)
     */
    private String sort;
    /**
     * Количество событий, которые нужно пропустить для формирования текущего набора
     */
    private int from;
    /**
     * Количество событий в наборе
     */
    private int size;

    /**
     * Создание нового объекта запроса с преобразованием входящих данных в нужный формат
     */
    public static PublicEventsRequest of(String text, Integer[] categories, boolean paid, String rangeStart,
                                         String rangeEnd, boolean onlyAvailable, String sort, int from, int size) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        PublicEventsRequest request = new PublicEventsRequest();
        request.setText(text);
        request.setCategories(Arrays.asList(categories));
        request.setPaid(paid);
        request.setRangeStart(rangeStart.equals("NULL") ? LocalDateTime.now() : LocalDateTime.parse(rangeStart, formatter));
        request.setRangeEnd(rangeEnd.equals("NULL") ? LocalDateTime.now().plusYears(1) : LocalDateTime.parse(rangeEnd, formatter));
        request.setOnlyAvailable(onlyAvailable);
        request.setSort(sort);
        request.setFrom(from);
        request.setSize(size);
        return request;
    }
}
