package ru.practicum.explore.event.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.explore.models.category.Category;
import ru.practicum.explore.repositroy.CategoryRepository;
import ru.practicum.explore.models.event.Event;
import ru.practicum.explore.repositroy.EventRepository;
import ru.practicum.explore.models.event.EventState;
import ru.practicum.explore.models.event.dto.AdminUpdateEventRequestDto;
import ru.practicum.explore.models.event.dto.EventFullDto;
import ru.practicum.explore.services.admin.AdminEventService;
import ru.practicum.explore.models.user.User;
import ru.practicum.explore.repositroy.UserRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AdminEventServiceImplTest {

    private final EventRepository eventRepo;
    private final AdminEventService adminService;
    private final CategoryRepository catRepo;
    private final UserRepository userRepo;

    @Autowired
    AdminEventServiceImplTest(EventRepository eventRepo, AdminEventService adminService, CategoryRepository catRepo, UserRepository userRepo) {
        this.eventRepo = eventRepo;
        this.adminService = adminService;
        this.catRepo = catRepo;
        this.userRepo = userRepo;
    }

    Event event = new Event();
    Event event2 = new Event();
    AdminUpdateEventRequestDto updateRequest = new AdminUpdateEventRequestDto();
    Category category = new Category();
    User initiator = new User();

    @BeforeEach
    void setUp() {
        initiator.setName("Name");
        initiator.setEmail("aa@aa.ru");
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

        updateRequest.setEventDate("2022-10-10 10:10:10");
        updateRequest.setAnnotation("New one");
        updateRequest.setCategoryId(category.getId());
    }

    @Test
    void getAllEvents() {
        assertEquals(2, adminService.getAllEvents(null, null, null, null,
                null, 0, 10).size());
    }

    @Test
    void editEvent() {
        EventFullDto dto = adminService.editEvent(event.getId(), updateRequest);
        assertEquals("2022-10-10 10:10:10", dto.getEventDate());
        assertEquals("New one", dto.getAnnotation());
    }

    @Test
    void publishEvent() {
        EventFullDto dto = adminService.publishEvent(event.getId());
        assertEquals("PUBLISHED", dto.getState());
        assertThrows(IllegalStateException.class, () -> adminService.publishEvent(event2.getId()));
    }

    @Test
    void rejectEvent() {
        EventFullDto dto = adminService.rejectEvent(event.getId());
        assertEquals("CANCELLED", dto.getState());
        assertThrows(IllegalStateException.class, () -> adminService.rejectEvent(event2.getId()));
    }
}