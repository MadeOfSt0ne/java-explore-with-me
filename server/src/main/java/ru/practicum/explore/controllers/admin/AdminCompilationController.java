package ru.practicum.explore.controllers.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.services.admin.AdminCompilationService;
import ru.practicum.explore.models.compilation.dto.CompilationDto;
import ru.practicum.explore.models.compilation.dto.NewCompilationDto;

/**
 * Приватный api для работы с подборками событий
 */
@Slf4j
@RestController
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationController {

    private final AdminCompilationService compilationService;

    /**
     * Добавление новой подборки событий
     */
    @PostMapping
    public CompilationDto addNewCompilation(@RequestBody NewCompilationDto dto) {
        log.info("ADMIN: Add new compilation={}", dto);
        return compilationService.addNewCompilation(dto);
    }

    /**
     * Удаление подборки
     */
    @DeleteMapping(path = "/{compId}")
    public void deleteCompilation(@PathVariable long compId) {
        log.info("ADMIN: Delete compilation id={}", compId);
        compilationService.deleteCompilation(compId);
    }

    /**
     * Удаление события из подборки
     */
    @DeleteMapping(path = "/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable long compId,
                                           @PathVariable long eventId) {
        log.info("ADMIN: Delete event id={} from compilation id={}", eventId, compId);
        compilationService.deleteEventFromCompilation(compId, eventId);
    }

    /**
     * Добавление события в подборку
     */
    @PatchMapping(path = "/{compId}/events/{eventId}")
    public void addEventToCompilation(@PathVariable long compId,
                                      @PathVariable long eventId) {
        log.info("ADMIN: Add event id={} to compilation id={}", eventId, compId);
        compilationService.addEventToCompilation(compId, eventId);
    }

    /**
     * Открепить категорию с главной страницы
     */
    @DeleteMapping(path = "/{compId}/pin")
    public void unpinCompilation(@PathVariable long compId) {
        log.info("ADMIN: Unpin compilation id={}", compId);
        compilationService.unpinCompilation(compId);
    }

    /**
     * Закрепить категорию на главной странице
     */
    @PatchMapping(path = "/{compId}/pin")
    public void pinCompilation(@PathVariable long compId) {
        log.info("ADMIN: Pin compilation id={}", compId);
        compilationService.pinCompilation(compId);
    }
}
