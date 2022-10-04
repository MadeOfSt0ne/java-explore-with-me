package ru.practicum.explore.services.client;

import ru.practicum.explore.models.compilation.dto.CompilationDto;

import java.util.List;

/**
 * Сервис для работы с подборками
 */
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
