package ru.practicum.explore.mappers;

import lombok.NoArgsConstructor;
import ru.practicum.explore.models.compilation.Compilation;
import ru.practicum.explore.models.compilation.dto.CompilationDto;
import ru.practicum.explore.models.compilation.dto.NewCompilationDto;
import ru.practicum.explore.models.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Маппер для подборок событий
 */
@NoArgsConstructor
public class CompilationMapper {

    /**
     * Создание дто из подборки
     */
    public static CompilationDto toCompilationDto(Compilation compilation) {
        List<Long> events = compilation.getEvents().stream()
                .map(Event::getId)
                .collect(Collectors.toList());
        return new CompilationDto(
                compilation.getId(),
                compilation.isPinned(),
                compilation.getTitle(),
                events
        );
    }

    /**
     * Создание списка дто из списка подборок
     */
    public static List<CompilationDto> toCompilationDto(List<Compilation> compilations) {
        List<CompilationDto> dtos = new ArrayList<>();
        for (Compilation comp : compilations) {
            dtos.add(toCompilationDto(comp));
        }
        return dtos;
    }

    /**
     * Создание новой подборки из дто и списка событий
     */
    public static Compilation toCompilation(NewCompilationDto newCompDto, Set<Event> events) {
        Compilation compilation = new Compilation();
        compilation.setPinned(newCompDto.isPinned());
        compilation.setTitle(newCompDto.getTitle());
        compilation.setEvents(events);
        return compilation;
    }
}
