package ru.practicum.explore.event.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
public class PublicEventsRequest {
    private String text;
    private List<Integer> categories;
    private boolean paid;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private boolean onlyAvailable;
    private Sort sort;
    private int from;
    private int size;

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
        request.setSort(Sort.valueOf(sort.toUpperCase()));
        request.setFrom(from);
        request.setSize(size);
        return request;
    }

    public enum Sort {
        EVENT_DATE, VIEWS
    }
}
