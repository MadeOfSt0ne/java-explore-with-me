package ru.practicum.stats.record;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats.record.dto.EndpointHit;
import ru.practicum.stats.record.dto.RecordMapper;
import ru.practicum.stats.record.dto.ViewStats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;

    /**
     * Сохранение информации о том, что к эндпоинту был запрос
     *
     * @param endpointHit тело запроса. включает название сервиса, ip и uri
     */
    @Override
    public void addRecord(EndpointHit endpointHit) {
        recordRepository.save(RecordMapper.toRecord(endpointHit));
    }

    /**
     * Получение статистики по посещениям
     *
     * @param start Дата и время начала диапазона за который нужно выгрузить статистику (в формате "yyyy-MM-dd HH:mm:ss")
     * @param end Дата и время конца диапазона за который нужно выгрузить статистику (в формате "yyyy-MM-dd HH:mm:ss")
     * @param uris Список uri для которых нужно выгрузить статистику
     * @param unique Нужно ли учитывать только уникальные посещения (только с уникальным ip)
     */
    @Override
    @Transactional(readOnly = true)
    public List<ViewStats> getRecords(String start, String end, String[] uris, boolean unique) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime from = LocalDateTime.parse(start, formatter);
        LocalDateTime to = LocalDateTime.parse(end, formatter);
        List<String> uri = Arrays.asList(uris);
        List<Record> records = recordRepository.findByTimestampBetweenAndUriIsIn(from, to, uri);

        /*QRecord record = QRecord.record;
        List<BooleanExpression> conditions = new ArrayList<>();
        conditions.add(record.timestamp.between(from, to));
        for (String s : uris) {
            conditions.add(record.uri.eq(s));
        }
        BooleanExpression finalCondition = conditions.stream()
                .reduce(BooleanExpression::and)
                .get();
        Iterable<Record> records = recordRepository.findAll(finalCondition);
        List<Record> result = new ArrayList<>();
        for (Record r : records) {
            result.add(r);
        }
        if (unique) {
            result = result.stream().distinct().collect(Collectors.toList());
        }*/
        return new ArrayList<>();
    }
}
