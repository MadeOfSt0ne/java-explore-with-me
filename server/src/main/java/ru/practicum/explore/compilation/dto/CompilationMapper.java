package ru.practicum.explore.compilation.dto;

import lombok.NoArgsConstructor;
import ru.practicum.explore.compilation.Compilation;
import ru.practicum.explore.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
public class CompilationMapper {

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

    public static List<CompilationDto> toCompilationDto(List<Compilation> compilations) {
        List<CompilationDto> dtos = new ArrayList<>();
        for (Compilation comp : compilations) {
            dtos.add(toCompilationDto(comp));
        }
        return dtos;
    }

    public static Compilation toCompilation(NewCompilationDto newCompDto, Set<Event> events) {
        return new Compilation(
                0,
                newCompDto.isPinned(),
                newCompDto.getTitle(),
                events
        );
    }
}
