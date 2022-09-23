package ru.practicum.stats.record;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RecordServiceTest {
    private final RecordRepository repository;
    private final RecordService service;

    @Autowired
    RecordServiceTest(RecordRepository repository, RecordService service) {
        this.repository = repository;
        this.service = service;
    }

    Record record1 = new Record();
    Record record2 = new Record();
    String[] uris = new String[1];

    @BeforeEach
    void setUp() {
    record1.setApp("app1");
    record1.setIp("1111");
    record1.setUri("uri1");

    record2.setApp("app2");
    record2.setIp("2222");
    record2.setUri("uri2");
    uris[0] = "uri2";
    }

    @Test
    void testGetRecords() {
        Record rec1 = repository.save(record1);
        Record rec2 = repository.save(record2);
        assertEquals(1, service.getRecords("2022-09-01 10:10:10", "2022-10-01 10:10:10", uris, false).size());
    }
}
