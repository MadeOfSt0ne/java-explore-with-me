package ru.practicum.explore.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.compilation.dto.CompilationDto;
import ru.practicum.explore.compilation.dto.NewCompilationDto;

@Slf4j
@RestController
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationController {

    private final CompilationService compilationService;

    @PostMapping
    public CompilationDto addNewCompilation(@RequestBody NewCompilationDto dto) {
        log.info("ADMIN: Add new compilation={}", dto);
        return compilationService.addNewCompilation(dto);
    }

    @DeleteMapping(path = "/{compId}")
    public void deleteCompilation(@PathVariable long compId) {
        log.info("ADMIN: Delete compilation id={}", compId);
        compilationService.deleteCompilation(compId);
    }

    @DeleteMapping(path = "/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable long compId,
                                           @PathVariable long eventId) {
        log.info("ADMIN: Delete event id={} from compilation id={}", eventId, compId);
        compilationService.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping(path = "/{compId}/events/{eventId}")
    public void addEventToCompilation(@PathVariable long compId,
                                      @PathVariable long eventId) {
        log.info("ADMIN: Add event id={} to compilation id={}", eventId, compId);
        compilationService.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping(path = "/{compId}/pin")
    public void unpinCompilation(@PathVariable long compId) {
        log.info("ADMIN: Unpin compilation id={}", compId);
        compilationService.unpinCompilation(compId);
    }

    @PatchMapping(path = "/{compId}/pin")
    public void pinCompilation(@PathVariable long compId) {
        log.info("ADMIN: Pin compilation id={}", compId);
        compilationService.pinCompilation(compId);
    }
}
