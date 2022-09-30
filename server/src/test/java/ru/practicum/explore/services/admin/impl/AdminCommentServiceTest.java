package ru.practicum.explore.services.admin.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.explore.models.category.Category;
import ru.practicum.explore.models.comment.Comment;
import ru.practicum.explore.models.comment.dto.CommentDto;
import ru.practicum.explore.models.event.Event;
import ru.practicum.explore.models.event.EventState;
import ru.practicum.explore.models.user.User;
import ru.practicum.explore.repositroy.CategoryRepository;
import ru.practicum.explore.repositroy.CommentRepository;
import ru.practicum.explore.repositroy.EventRepository;
import ru.practicum.explore.repositroy.UserRepository;
import ru.practicum.explore.services.admin.AdminCommentService;

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
    }

    @Test
    void testEditComment() {
        final CommentDto dto = service.editComment(comment.getId(), "Updated comment");
        assertEquals("Updated comment", dto.getText());
        assertTrue(dto.getEditedOn().contains("2022"));
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
