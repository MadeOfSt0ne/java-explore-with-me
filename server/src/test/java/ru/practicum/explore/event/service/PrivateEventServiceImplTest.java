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
import ru.practicum.explore.models.event.dto.EventFullDto;
import ru.practicum.explore.models.event.dto.NewEventDto;
import ru.practicum.explore.models.event.dto.UpdateEventRequestDto;
import ru.practicum.explore.services.client.PrivateEventService;
import ru.practicum.explore.models.participationRequest.ParticipationRequest;
import ru.practicum.explore.repositroy.ParticipationRequestRepository;
import ru.practicum.explore.models.participationRequest.RequestStatus;
import ru.practicum.explore.models.participationRequest.dto.ParticipationRequestDto;
import ru.practicum.explore.models.user.User;
import ru.practicum.explore.repositroy.UserRepository;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PrivateEventServiceImplTest {

    private final EventRepository eventRepo;
    private final UserRepository userRepo;
    private final CategoryRepository categoryRepo;
    private final PrivateEventService privateService;
    private final ParticipationRequestRepository partRepo;

    @Autowired
    PrivateEventServiceImplTest(EventRepository eventRepo, UserRepository userRepo, CategoryRepository categoryRepo, PrivateEventService privateService, ParticipationRequestRepository partRepo) {
        this.eventRepo = eventRepo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
        this.privateService = privateService;
        this.partRepo = partRepo;
    }

    private final Event event = new Event();
    private final Event event2 = new Event();
    private final Category category = new Category();
    private final User initiator = new User();
    private final User requester = new User();
    private final NewEventDto newDto = new NewEventDto();
    private final UpdateEventRequestDto updateEventRequestDto = new UpdateEventRequestDto();
    private final ParticipationRequest request = new ParticipationRequest();

    @BeforeEach
    void setUp() {
        initiator.setName("Initiator");
        initiator.setEmail("init@init.com");
        userRepo.save(initiator);

        requester.setName("Requester");
        requester.setEmail("req@req.com");
        userRepo.save(requester);

        category.setName("category");
        categoryRepo.save(category);

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

        newDto.setAnnotation("The new event!");
        newDto.setCategoryId(category.getId());
        newDto.setDescription("Some cool description");
        newDto.setEventDate("2022-10-15 10:10:10");
        newDto.setPaid(true);
        newDto.setParticipantLimit(10);
        newDto.setRequestModeration(true);
        newDto.setTitle("The new one!");
        newDto.setLocation(new NewEventDto.Location(1.1f, 2.2f));

        updateEventRequestDto.setEventId(event.getId());
        updateEventRequestDto.setAnnotation("New annotation");
        updateEventRequestDto.setDescription("New description");
        updateEventRequestDto.setEventDate("2022-10-17 10:10:10");

        request.setEvent(event);
        request.setRequester(requester);
        request.setStatus(RequestStatus.PENDING);
        partRepo.save(request);
    }

    @Test
    void getOwnEvents() {
        assertEquals(2, privateService.getOwnEvents(initiator.getId(), 0, 10).size());
        assertThrows(NoSuchElementException.class, () -> privateService.getOwnEvents(3L, 0, 10));
    }

    @Test
    void updateEvent() {
        EventFullDto dto = privateService.updateEvent(initiator.getId(), updateEventRequestDto);
        assertEquals(event.getTitle(), dto.getTitle());
        assertEquals(event.getParticipantLimit(), dto.getParticipantLimit());
        assertEquals(updateEventRequestDto.getAnnotation(), dto.getAnnotation());
        assertEquals(updateEventRequestDto.getEventDate(), dto.getEventDate());
    }

    @Test
    void addNewEvent() {
        EventFullDto dto = privateService.addNewEvent(initiator.getId(), newDto);
        assertEquals(newDto.getAnnotation(), dto.getAnnotation());
        assertEquals(newDto.getEventDate(), dto.getEventDate());
        assertEquals("PENDING", dto.getState());
        assertEquals(newDto.getTitle(), dto.getTitle());
    }

    @Test
    void getEvent() {
        EventFullDto dto = privateService.getEvent(initiator.getId(), event.getId());
        assertEquals(event.getAnnotation(), dto.getAnnotation());
        assertEquals(event.getParticipantLimit(), dto.getParticipantLimit());
    }

    @Test
    void cancelEvent() {
        assertEquals(EventState.PENDING, event.getEventState());
        EventFullDto dto = privateService.cancelEvent(initiator.getId(), event.getId());
        assertEquals("CANCELLED", dto.getState());
    }

    @Test
    void getRequests() {
        assertEquals(1, privateService.getRequests(initiator.getId(), event.getId()).size());
        assertEquals(requester.getId(), privateService.getRequests(initiator.getId(), event.getId()).get(0).getRequester());
    }

    @Test
    void confirmRequest() {
        assertEquals(RequestStatus.PENDING, request.getStatus());
        ParticipationRequestDto dto = privateService.confirmRequest(initiator.getId(), event.getId(), request.getId());
        assertEquals("CONFIRMED", dto.getStatus());
    }

    @Test
    void rejectRequest() {
        assertEquals(RequestStatus.PENDING, request.getStatus());
        ParticipationRequestDto dto = privateService.rejectRequest(initiator.getId(), event.getId(), request.getId());
        assertEquals("REJECTED", dto.getStatus());
    }
}