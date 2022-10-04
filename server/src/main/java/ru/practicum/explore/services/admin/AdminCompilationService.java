package ru.practicum.explore.services.admin;

import ru.practicum.explore.models.compilation.dto.CompilationDto;
import ru.practicum.explore.models.compilation.dto.NewCompilationDto;

/**
 * Сервис для работы с подборками событий
 */
public interface AdminCompilationService {

    /**
     * Добавление новой подборки
     */
    CompilationDto addNewCompilation(NewCompilationDto newCompilationDto);

    /**
     * Удаление подборки
     */
    void deleteCompilation(long compId);

    /**
     * Удаление события из подборки
     */
    void deleteEventFromCompilation(long compId, long eventId);

    /**
     * Добавление события в подборку
     */
    void addEventToCompilation(long compId, long eventId);

    /**
     * Открепить подборку на главной странице
     */
    void unpinCompilation(long compId);

    /**
     * Прикрепить подборку на главной странице
     */
    void pinCompilation(long compId);
}
