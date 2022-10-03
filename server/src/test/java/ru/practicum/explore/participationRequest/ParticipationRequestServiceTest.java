package ru.practicum.explore.participationRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.explore.models.event.Event;
import ru.practicum.explore.repository.EventRepository;
import ru.practicum.explore.models.event.EventState;
import ru.practicum.explore.models.participationRequest.ParticipationRequest;
import ru.practicum.explore.repository.ParticipationRequestRepository;
import ru.practicum.explore.models.participationRequest.RequestStatus;
import ru.practicum.explore.models.participationRequest.dto.ParticipationRequestDto;
import ru.practicum.explore.services.client.ParticipationRequestService;
import ru.practicum.explore.models.user.User;
import ru.practicum.explore.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ParticipationRequestServiceTest {

    private final ParticipationRequestService service;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ParticipationRequestRepository partyRepo;

    @Autowired
    ParticipationRequestServiceTest(ParticipationRequestService service, EventRepository eventRepository,
                                    UserRepository userRepository, ParticipationRequestRepository partyRepo) {
        this.service = service;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.partyRepo = partyRepo;
    }

    private final Event event1 = new Event();
    private final Event event2 = new Event();
    private final User initiator = new User();
    private final User requester = new User();
    private final ParticipationRequest request = new ParticipationRequest();
    private final ParticipationRequest request2 = new ParticipationRequest();

    @BeforeEach
    void setUp() {
        initiator.setName("initiator");
        initiator.setEmail("init@init.com");
        userRepository.save(initiator);

        requester.setName("requester");
        requester.setEmail("req@req.com");
        userRepository.save(requester);

        event1.setInitiator(initiator);
        event1.setParticipantLimit(10);
        event1.setRequestModeration(true);
        event1.setEventState(EventState.PUBLISHED);
        eventRepository.save(event1);

        event2.setInitiator(initiator);
        event2.setParticipantLimit(10);
        event2.setEventState(EventState.PUBLISHED);
        eventRepository.save(event2);

        request.setEvent(event1);
        request.setRequester(requester);
        request.setStatus(RequestStatus.PENDING);

        request2.setEvent(event2);
        request2.setRequester(requester);
        request2.setStatus(RequestStatus.PENDING);
    }

    @Test
    void contextLoads() {
        assertNotNull(service);
    }

    @Test
    void getOwnRequests() {
        partyRepo.save(request);
        partyRepo.save(request2);
        assertEquals(2, service.getOwnRequests(requester.getId()).size());
    }

    @Test
    void addNewRequest() {
        service.addNewRequest(requester.getId(), event1.getId());
        assertEquals(1, service.getOwnRequests(requester.getId()).size());
        assertThrows(IllegalStateException.class, () -> service.addNewRequest(initiator.getId(), event1.getId()));
    }

    @Test
    void cancelOwnRequest() {
        final ParticipationRequestDto dto = service.addNewRequest(requester.getId(), event1.getId());
        assertEquals("PENDING", dto.getStatus());
        final ParticipationRequestDto cancelledDto = service.cancelOwnRequest(requester.getId(), dto.getId());
        assertEquals("CANCELLED", cancelledDto.getStatus());
    }
}