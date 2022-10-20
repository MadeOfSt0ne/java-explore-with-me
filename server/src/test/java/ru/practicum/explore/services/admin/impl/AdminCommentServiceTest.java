package ru.practicum.explore.services.admin.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.explore.models.category.Category;
import ru.practicum.explore.models.comment.Comment;
import ru.practicum.explore.models.comment.dto.ShortCommentDto;
import ru.practicum.explore.models.event.Event;
import ru.practicum.explore.models.event.EventState;
import ru.practicum.explore.models.user.User;
import ru.practicum.explore.repository.CategoryRepository;
import ru.practicum.explore.repository.CommentRepository;
import ru.practicum.explore.repository.EventRepository;
import ru.practicum.explore.repository.UserRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AdminCommentServiceTest {
    private final AdminCommentService service;
    private final CommentRepository commentRepo;
    private final UserRepository userRepo;
    private final EventRepository eventRepo;
    private final CategoryRepository categoryRepo;

    @Autowired
    AdminCommentServiceTest(AdminCommentService service, CommentRepository commentRepo, UserRepository userRepo, EventRepository eventRepo, CategoryRepository categoryRepo) {
        this.service = service;
        this.commentRepo = commentRepo;
        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
        this.categoryRepo = categoryRepo;
    }

    private final Event event = new Event();
    private final User initiator = new User();
    private final Category category = new Category();
    private final Comment comment = new Comment();
    private final ShortCommentDto updated = new ShortCommentDto();

    @BeforeEach
    void setUp() {
        initiator.setName("Initiator");
        initiator.setEmail("init@init.com");
        userRepo.save(initiator);

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

        comment.setEvent(event);
        comment.setAuthor(initiator);
        comment.setText("Comment");
        commentRepo.save(comment);

        updated.setText("Updated comment");
    }

    @Test
    void testEditComment() {
        final Comment comm = service.editComment(comment.getId(), updated);
        assertEquals("Updated comment", comm.getText());
    }

    @Test
    void testRemoveComment() {
        Comment comment1 = commentRepo.findById(comment.getId()).orElse(null);
        assertNotNull(comment1);
        service.removeComment(comment.getId());
        Comment comment2 = commentRepo.findById(comment.getId()).orElse(null);
        assertNull(comment2);
    }
}
