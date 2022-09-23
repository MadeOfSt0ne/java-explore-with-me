package ru.practicum.explore.compilation.service;

import ru.practicum.explore.compilation.dto.CompilationDto;
import ru.practicum.explore.compilation.dto.NewCompilationDto;

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
     * Добвалене события в подборку
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
