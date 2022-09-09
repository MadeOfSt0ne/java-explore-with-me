package ru.practicum.explore.compilation;

import ru.practicum.explore.compilation.dto.CompilationDto;
import ru.practicum.explore.compilation.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {

    /**
     * Получение подборок событий
     */
    List<CompilationDto> getCompilations(boolean pinned, int from, int size);

    /**
     * Получение подборки события по его идентификатору
     */
    CompilationDto getCompilation(long compId);

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
