package ru.practicum.explore.event.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.explore.category.Category;
import ru.practicum.explore.category.CategoryRepository;
import ru.practicum.explore.event.Event;
import ru.practicum.explore.event.EventRepository;
import ru.practicum.explore.event.EventState;
import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.event.dto.PublicEventsRequest;
import ru.practicum.explore.exception.NotFoundException;
import ru.practicum.explore.user.User;
import ru.practicum.explore.user.UserRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PublicEventServiceImplTest {

    private final EventRepository eventRepo;
    private final PublicEventService publicService;
    private final CategoryRepository catRepo;
    private final UserRepository userRepo;

    @Autowired
    PublicEventServiceImplTest(EventRepository eventRepo, PublicEventService publicService,
                               CategoryRepository catRepo, UserRepository userRepo) {
        this.eventRepo = eventRepo;
        this.publicService = publicService;
        this.catRepo = catRepo;
        this.userRepo = userRepo;
    }

    Event event = new Event();
    Event event2 = new Event();
    PublicEventsRequest request;
    Category category = new Category();
    User initiator = new User();

    @BeforeEach
    void setUp() {
        initiator.setName("Name");
        userRepo.save(initiator);

        category.setName("category");
        catRepo.save(category);

        event.setInitiator(initiator);
        event.setEventState(EventState.PENDING);
        event.setEventDate(LocalDateTime.now().plusDays(1).withNano(0));
        event.setPublishedOn(LocalDateTime.now().plusDays(1).withNano(0));
        event.setPaid(true);
        event.setParticipantLimit(10);
        event.setConfirmedRequests(5);
        event.setRequestModeration(true);
        event.setTitle("title");
        event.setAnnotation("Old one");
        event.setLon(1.1f);
        event.setLat(2.2f);
        event.setCategory(category);
        eventRepo.save(event);

        event2.setInitiator(initiator);
        event2.setEventState(EventState.PUBLISHED);
        event2.setEventDate(LocalDateTime.now().plusDays(2).withNano(0));
        event2.setPublishedOn(LocalDateTime.now().plusDays(1).withNano(0));
        event2.setPaid(true);
        event2.setParticipantLimit(10);
        event2.setConfirmedRequests(5);
        event2.setRequestModeration(true);
        event2.setTitle("title");
        event2.setAnnotation("Second event");
        event2.setLon(1.7f);
        event2.setLat(2.6f);
        event2.setCategory(category);
        eventRepo.save(event2);

        request = PublicEventsRequest.of("", new Integer[0], true, "NULL", "NULL", false,
                "EVENT_DATE", 0, 10);
    }

    @Test
    void getEventsFiltered() {
        assertEquals(1, publicService.getEventsFiltered(request).size());
    }

    @Test
    void getEvent() {
        EventFullDto dto = publicService.getEvent(event2.getId());
        assertEquals(event2.getAnnotation(), dto.getAnnotation());
        assertThrows(NotFoundException.class, () -> publicService.getEvent(event.getId()));
    }
}