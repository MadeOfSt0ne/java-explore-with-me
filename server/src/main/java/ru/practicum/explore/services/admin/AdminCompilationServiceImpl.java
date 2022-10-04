package ru.practicum.explore.services.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore.mappers.CompilationMapper;
import ru.practicum.explore.models.compilation.Compilation;
import ru.practicum.explore.models.compilation.CompilationRepository;
import ru.practicum.explore.models.compilation.dto.CompilationDto;
import ru.practicum.explore.models.compilation.dto.NewCompilationDto;
import ru.practicum.explore.models.event.Event;
import ru.practicum.explore.models.event.EventRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminCompilationServiceImpl implements AdminCompilationService {

    private final CompilationRepository compRepository;
    private final EventRepository eventRepository;

    @Override
    public CompilationDto addNewCompilation(NewCompilationDto newCompilationDto) {
        Set<Event> events = newCompilationDto.getEvents().stream()
                .map((id) -> eventRepository.findById(id).orElseThrow())
                .collect(Collectors.toSet());
        Compilation newComp = compRepository.save(CompilationMapper.toCompilation(newCompilationDto, events));
        return CompilationMapper.toCompilationDto(newComp);
    }

    @Override
    public void deleteCompilation(long compId) {
        compRepository.deleteById(compId);
    }

    @Override
    public void deleteEventFromCompilation(long compId, long eventId) {
        Compilation comp = compRepository.findById(compId).orElseThrow();
        Event deletedEvent = eventRepository.findById(eventId).orElseThrow();
        comp.getEvents().removeIf(e -> e.getId() == eventId);
        compRepository.save(comp);
    }

    @Override
    public void addEventToCompilation(long compId, long eventId) {
        Compilation comp = compRepository.findById(compId).orElseThrow();
        Event addedEvent = eventRepository.findById(eventId).orElseThrow();
        comp.getEvents().add(addedEvent);
        compRepository.save(comp);
    }

    @Override
    public void unpinCompilation(long compId) {
        Compilation comp = compRepository.findById(compId).orElseThrow();
        comp.setPinned(false);
        compRepository.save(comp);
    }

    @Override
    public void pinCompilation(long compId) {
        Compilation comp = compRepository.findById(compId).orElseThrow();
        comp.setPinned(true);
        compRepository.save(comp);
    }

}
