package ru.practicum.explore.participationRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.explore.category.Category;
import ru.practicum.explore.event.Event;
import ru.practicum.explore.event.EventRepository;
import ru.practicum.explore.participationRequest.dto.ParticipationRequestDto;
import ru.practicum.explore.user.User;
import ru.practicum.explore.user.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ParticipationRequestServiceTest {

    private final ParticipationRequestService service;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Autowired
    ParticipationRequestServiceTest(ParticipationRequestService service, EventRepository eventRepository,
                                    UserRepository userRepository) {
        this.service = service;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    private Event event1;
    private Event event2;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "requester", "requester@user.com");
        User user2 = new User(2L, "initiator", "initiator@user.com");
        Category category = new Category(1L, "cat");
        event1 = new Event(1L, user2, "a", category, "b", "d", true, 10, false, "t");
        event2 = new Event(2L, user2, "a", category, "b", "d", true, 10, false, "t");
    }

    @Test
    void contextLoads() {
        assertNotNull(service);
    }

    @Test
    void getOwnRequests() {
        final Event ev = eventRepository.save(event1);
        final Event ev2 = eventRepository.save(event2);
        final User requester = userRepository.save(user);
        final ParticipationRequestDto dto = service.addNewRequest(requester.getId(), ev.getId());
        final ParticipationRequestDto dto2 = service.addNewRequest(requester.getId(), ev2.getId());
        assertEquals(List.of(dto, dto2), service.getOwnRequests(requester.getId()));
    }

    @Test
    void addNewRequest() {
        final Event ev = eventRepository.save(event1);
        final User requester = userRepository.save(user);
        service.addNewRequest(requester.getId(), ev.getId());
        assertEquals(1, service.getOwnRequests(requester.getId()).size());
    }

    @Test
    void cancelOwnRequest() {
        final Event ev = eventRepository.save(event1);
        final User requester = userRepository.save(user);
        final ParticipationRequestDto dto = service.addNewRequest(requester.getId(), ev.getId());
        assertEquals("PENDING", dto.getStatus());
        final ParticipationRequestDto cancelledDto = service.cancelOwnRequest(requester.getId(), dto.getId());
        assertEquals("CANCELLED", cancelledDto.getStatus());
    }
}