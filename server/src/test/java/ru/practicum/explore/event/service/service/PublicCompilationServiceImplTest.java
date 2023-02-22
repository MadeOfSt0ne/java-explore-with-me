package ru.practicum.explore.event.service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.explore.models.compilation.Compilation;
import ru.practicum.explore.repository.CompilationRepository;
import ru.practicum.explore.models.compilation.dto.CompilationDto;
import ru.practicum.explore.models.compilation.dto.NewCompilationDto;
import ru.practicum.explore.services.admin.AdminCompilationService;
import ru.practicum.explore.services.client.PublicCompilationService;
import ru.practicum.explore.models.event.Event;
import ru.practicum.explore.repository.EventRepository;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PublicCompilationServiceImplTest {

    private final AdminCompilationService adminService;
    private final PublicCompilationService publicService;
    private final CompilationRepository repository;
    private final EventRepository eventRepository;

    @Autowired
    PublicCompilationServiceImplTest(AdminCompilationService adminService, PublicCompilationService publicService,
                                     CompilationRepository repository, EventRepository eventRepository) {
        this.adminService = adminService;
        this.publicService = publicService;
        this.repository = repository;
        this.eventRepository = eventRepository;
    }

    Compilation compilation = new Compilation();
    Compilation compilation1 = new Compilation();
    Event event = new Event();
    Event event2 = new Event();
    NewCompilationDto newCompDto;

    @BeforeEach
    void setUp() {
        eventRepository.save(event);
        eventRepository.save(event2);

        compilation.setPinned(true);
        compilation.setTitle("comp1");
        compilation.setEvents(Set.of(event));

        compilation1.setPinned(false);
        compilation1.setTitle("comp2");
        compilation1.setEvents(Set.of(event));

        newCompDto = new NewCompilationDto(true, "title", List.of(event.getId()));
    }

    @Test
    void getCompilations() {
        Compilation comp1 = repository.save(compilation);
        Compilation comp2 = repository.save(compilation1);
        assertEquals(1, publicService.getCompilations(true, 0, 10).size());
        assertEquals(1, publicService.getCompilations(false, 0, 10).size());
    }

    @Test
    void getCompilation() {
        Compilation comp1 = repository.save(compilation);
        assertNotNull(publicService.getCompilation(comp1.getId()));
        assertEquals("comp1", publicService.getCompilation(comp1.getId()).getTitle());
    }

    @Test
    void addNewCompilation() {
        CompilationDto dto = adminService.addNewCompilation(newCompDto);
        assertNotNull(dto);
    }

    @Test
    void deleteCompilation() {
        Compilation comp1 = repository.save(compilation);
        assertEquals(1, publicService.getCompilations(true, 0, 10).size());
        adminService.deleteCompilation(comp1.getId());
        assertEquals(0, publicService.getCompilations(true, 0, 10).size());
    }

    @Test
    void deleteEventFromCompilation() {
        Compilation comp1 = repository.save(compilation);
        System.out.println(comp1.getEvents());
        adminService.deleteEventFromCompilation(comp1.getId(), event.getId());
        assertEquals(0, publicService.getCompilation(comp1.getId()).getEvents().size());
    }

    @Test
    void addEventToCompilation() {
        Compilation comp1 = repository.save(compilation);
        adminService.addEventToCompilation(comp1.getId(), event2.getId());
        assertEquals(2, publicService.getCompilation(comp1.getId()).getEvents().size());
    }

    @Test
    void unpinCompilation() {
        Compilation comp1 = repository.save(compilation);
        adminService.unpinCompilation(comp1.getId());
        assertFalse(publicService.getCompilation(comp1.getId()).isPinned());
    }

    @Test
    void pinCompilation() {
        Compilation comp2 = repository.save(compilation1);
        adminService.pinCompilation(comp2.getId());
        assertTrue(publicService.getCompilation(comp2.getId()).isPinned());
    }
}