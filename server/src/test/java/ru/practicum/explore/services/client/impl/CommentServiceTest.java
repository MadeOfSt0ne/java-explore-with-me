package ru.practicum.explore.services.client.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.explore.exceptions.ValidationException;
import ru.practicum.explore.models.category.Category;
import ru.practicum.explore.models.comment.Comment;
import ru.practicum.explore.models.comment.dto.ShortCommentDto;
import ru.practicum.explore.models.event.Event;
import ru.practicum.explore.models.event.EventState;
import ru.practicum.explore.models.user.User;
import ru.practicum.explore.repository.CategoryRepository;
import ru.practicum.explore.repository.EventRepository;
import ru.practicum.explore.repository.UserRepository;

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
    private final User anotherUser = new User();
    private final Category category = new Category();
    private final ShortCommentDto shortCommentDto = new ShortCommentDto();
    private final ShortCommentDto empty = new ShortCommentDto();
    private final ShortCommentDto newComment = new ShortCommentDto();

    @BeforeEach
    void setUp() {
        initiator.setName("Initiator");
        initiator.setEmail("init@init.com");
        userRepo.save(initiator);

        banned.setName("Banned user");
        banned.setEmail("bbb@bbb.ru");
        banned.setBanned(true);
        userRepo.save(banned);

        anotherUser.setName("Another user");
        anotherUser.setEmail("an@an.ru");
        userRepo.save(anotherUser);

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

        shortCommentDto.setText("Some comment");
        empty.setText("");
        newComment.setText("New");
    }

    @Test
    void addComment() {
        final Comment comm = service.addComment(initiator.getId(), event.getId(), shortCommentDto);
        assertEquals(comm.getText(), "Some comment");
        assertEquals(comm.getAuthor().getName(), initiator.getName());
        assertEquals(comm.getEvent().getTitle(), event.getTitle());
    }

    @Test
    void editComment() {
        final Comment comm = service.addComment(initiator.getId(), event.getId(), shortCommentDto);
        assertNull(comm.getEditedOn());
        final Comment edited = service.editComment(initiator.getId(), event.getId(), newComment);
        assertEquals(edited.getText(), "New");
        assertNotNull(edited.getEditedOn());
    }

    @Test
    void getComments() {
        assertEquals(0, service.getComments(initiator.getId(), 0, 10).size());
        final Comment comm = service.addComment(initiator.getId(), event.getId(), shortCommentDto);
        assertEquals(1, service.getComments(initiator.getId(), 0, 10).size());
    }

    @Test
    void removeComment() {
        assertEquals(0, service.getComments(initiator.getId(), 0, 10).size());
        final Comment comm = service.addComment(initiator.getId(), event.getId(), shortCommentDto);
        assertEquals(1, service.getComments(initiator.getId(), 0, 10).size());
        service.removeComment(initiator.getId(), comm.getId());
        assertEquals(0, service.getComments(initiator.getId(), 0, 10).size());
    }

    @Test
    void testAddEmptyComment() {
        assertThrows(ValidationException.class, () -> service.addComment(initiator.getId(), event.getId(), empty));
    }

    @Test
    void testEditCommentWithEmptyText() {
        final Comment comm = service.addComment(initiator.getId(), event.getId(), shortCommentDto);
        assertThrows(ValidationException.class, () -> service.editComment(initiator.getId(), comm.getId(), empty));
    }

    @Test
    void testEditCommentByAnotherUser() {
        final Comment comm = service.addComment(initiator.getId(), event.getId(), shortCommentDto);
        assertThrows(IllegalStateException.class, () -> service.editComment(anotherUser.getId(), comm.getId(), newComment));
    }

    @Test
    void testDeleteCommentByAnotherUser() {
        final Comment comm = service.addComment(initiator.getId(), event.getId(), shortCommentDto);
        assertThrows(IllegalStateException.class, () -> service.removeComment(anotherUser.getId(), comm.getId()));
    }

    @Test
    void testBannedUser() {
        assertThrows(IllegalStateException.class, () -> service.addComment(banned.getId(), event.getId(), shortCommentDto));
    }

    @Test
    void testEditCommentByBannedUser() {
        final Comment comm = service.addComment(initiator.getId(), event.getId(), shortCommentDto);
        initiator.setBanned(true);
        userRepo.save(initiator);
        assertThrows(IllegalStateException.class, () -> service.editComment(initiator.getId(), comm.getId(), newComment));
    }

    @Test
    void testRemoveCommentByBannedUser() {
        final Comment comm = service.addComment(initiator.getId(), event.getId(), shortCommentDto);
        initiator.setBanned(true);
        userRepo.save(initiator);
        assertThrows(IllegalStateException.class, () -> service.removeComment(initiator.getId(), comm.getId()));
    }
}