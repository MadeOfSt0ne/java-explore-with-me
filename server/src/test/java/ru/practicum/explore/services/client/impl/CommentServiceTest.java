package ru.practicum.explore.services.client.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.explore.exceptions.ValidationException;
import ru.practicum.explore.models.category.Category;
import ru.practicum.explore.models.comment.dto.CommentDto;
import ru.practicum.explore.models.event.Event;
import ru.practicum.explore.models.event.EventState;
import ru.practicum.explore.models.user.User;
import ru.practicum.explore.repositroy.CategoryRepository;
import ru.practicum.explore.repositroy.EventRepository;
import ru.practicum.explore.repositroy.UserRepository;
import ru.practicum.explore.services.client.PrivateCommentService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CommentServiceTest {

    private final PrivateCommentService service;
    private final UserRepository userRepo;
    private final EventRepository eventRepo;
    private final CategoryRepository categoryRepo;

    @Autowired
    CommentServiceTest(PrivateCommentService service, UserRepository userRepo, EventRepository eventRepo, CategoryRepository categoryRepo) {
        this.service = service;
        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
        this.categoryRepo = categoryRepo;
    }

    private final Event event = new Event();
    private final User initiator = new User();
    private final User banned = new User();
    private final Category category = new Category();

    @BeforeEach
    void setUp() {
        initiator.setName("Initiator");
        initiator.setEmail("init@init.com");
        userRepo.save(initiator);

        banned.setName("Banned user");
        banned.setEmail("bbb@bbb.ru");
        banned.setBanned(true);
        userRepo.save(banned);

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
    }

    @Test
    void addComment() {
        final CommentDto dto = service.addComment(initiator.getId(), event.getId(), "Some comment");
        assertEquals(dto.getText(), "Some comment");
        assertEquals(dto.getUser().getName(), initiator.getName());
        assertEquals(dto.getEvent().getTitle(), event.getTitle());
    }

    @Test
    void editComment() {
        final CommentDto dto = service.addComment(initiator.getId(), event.getId(), "Some comment");
        assertEquals("Not edited.", dto.getEditedOn());
        final CommentDto edited = service.editComment(initiator.getId(), event.getId(), "New");
        assertEquals(edited.getText(), "New");
        assertNotEquals("Not edited.", edited.getEditedOn());
    }

    @Test
    void getComments() {
        assertEquals(0, service.getComments(initiator.getId()).size());
        final CommentDto dto = service.addComment(initiator.getId(), event.getId(), "Some comment");
        assertEquals(1, service.getComments(initiator.getId()).size());
    }

    @Test
    void removeComment() {
        assertEquals(0, service.getComments(initiator.getId()).size());
        final CommentDto dto = service.addComment(initiator.getId(), event.getId(), "Some comment");
        assertEquals(1, service.getComments(initiator.getId()).size());
        service.removeComment(initiator.getId(), dto.getId());
        assertEquals(0, service.getComments(initiator.getId()).size());
    }

    @Test
    void testAddEmptyComment() {
        assertThrows(ValidationException.class, () -> service.addComment(initiator.getId(), event.getId(), ""));
    }

    @Test
    void testEditCommentWithEmptyText() {
        final CommentDto dto = service.addComment(initiator.getId(), event.getId(), "Some comment");
        assertThrows(ValidationException.class, () -> service.editComment(initiator.getId(), dto.getId(), ""));
    }

    @Test
    void testEditCommentByAnotherUser() {
        final CommentDto dto = service.addComment(initiator.getId(), event.getId(), "Some comment");
        assertThrows(IllegalStateException.class, () -> service.editComment(5L, dto.getId(), "new comment"));
    }

    @Test
    void testDeleteCommentByAnotherUser() {
        final CommentDto dto = service.addComment(initiator.getId(), event.getId(), "Some comment");
        assertThrows(IllegalStateException.class, () -> service.removeComment(5L, dto.getId()));
    }

    @Test
    void testBannedUser() {
        assertThrows(IllegalStateException.class, () -> service.addComment(banned.getId(), event.getId(), "comment"));
    }
}