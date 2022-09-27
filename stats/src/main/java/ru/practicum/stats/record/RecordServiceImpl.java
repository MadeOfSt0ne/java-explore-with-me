package ru.practicum.stats.record;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats.record.dto.EndpointHit;
import ru.practicum.stats.record.dto.RecordMapper;
import ru.practicum.stats.record.dto.ViewStats;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
        LocalDateTime from = LocalDateTime.parse(start, FORMATTER);
        LocalDateTime to = LocalDateTime.parse(end, FORMATTER);
        List<String> uriList = Arrays.asList(uris);

        String sqlQuery = "SELECT app, uri, count(hits) as hits " +
                "FROM (Select Distinct app, uri, 1 as hits FROM records r " +
                "where r.uri IN (:uris) and timestamp Between :start and :end) as p " +
                "group by p.app, p.uri";

        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("start", from);
        paramSource.addValue("end", to);
        paramSource.addValue("uris", uriList);

        return jdbcTemplate.query(
                unique ? sqlQuery : sqlQuery.replace("Distinct", ""),
                paramSource,
                (rs, rowNum) -> makeViewStats(rs));
    }

    private ViewStats makeViewStats(ResultSet rs) throws SQLException {
        ViewStats viewStats = new ViewStats();
        viewStats.setApp(rs.getString("app"));
        viewStats.setUri(rs.getString("uri"));
        viewStats.setHits(rs.getInt("hits"));
        return viewStats;
    }
}
