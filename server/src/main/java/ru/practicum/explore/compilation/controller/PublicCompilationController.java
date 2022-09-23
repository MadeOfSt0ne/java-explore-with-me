package ru.practicum.explore.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.compilation.dto.CompilationDto;
import ru.practicum.explore.compilation.service.PublicCompilationService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
public class PublicCompilationController {

    private final PublicCompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(value = "pinned", required = false, defaultValue = "false") boolean pinned,
                                                @RequestParam(value = "from", required = false, defaultValue = "0") int from,
                                                @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("PUBLIC: Get compilations with pinned={}, from={}, size={}", pinned, from, size);
        return compilationService.getCompilations(pinned, from, size);
    }

    @GetMapping(path = "/{compId}")
    public CompilationDto getCompilation(@PathVariable long compId) {
        log.info("PUBLIC: Get compilation id={}", compId);
        return compilationService.getCompilation(compId);
    }
}
