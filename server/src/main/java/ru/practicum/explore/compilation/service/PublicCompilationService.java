package ru.practicum.explore.compilation.service;

import ru.practicum.explore.compilation.dto.CompilationDto;

import java.util.List;

public interface PublicCompilationService {

    /**
     * Получение подборок событий
     */
    List<CompilationDto> getCompilations(boolean pinned, int from, int size);

    /**
     * Получение подборки события по его идентификатору
     */
    CompilationDto getCompilation(long compId);
}
