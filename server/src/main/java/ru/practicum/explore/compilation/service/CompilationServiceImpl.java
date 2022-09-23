package ru.practicum.explore.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explore.compilation.Compilation;
import ru.practicum.explore.compilation.CompilationRepository;
import ru.practicum.explore.compilation.dto.CompilationDto;
import ru.practicum.explore.compilation.dto.CompilationMapper;
import ru.practicum.explore.compilation.dto.NewCompilationDto;
import ru.practicum.explore.event.Event;
import ru.practicum.explore.event.EventRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements AdminCompilationService, PublicCompilationService {

    private final CompilationRepository compRepository;
    private final EventRepository eventRepository;

    /**
     * Получение подборок событий
     *
     * @param pinned true/false закреплена подборка или нет
     * @param from количество подборок, которые нужно пропустить для формирования текущего набора
     * @param size количество подборок в наборе
     */
    @Override
    public List<CompilationDto> getCompilations(boolean pinned, int from, int size) {
        Pageable pageable = PageRequest.of(from, size);
        Page<Compilation> compilations = compRepository.findAllByPinned(pageable, pinned);
        return CompilationMapper.toCompilationDto(compilations.toList());
    }

    /**
     * Получение подборки события по его идентификатору
     *
     * @param compId id подборки
     */
    @Override
    public CompilationDto getCompilation(long compId) {
        Compilation compilation = compRepository.findById(compId).orElseThrow();
        return CompilationMapper.toCompilationDto(compilation);
    }

    /**
     * Добавление новой подборки
     *
     * @param newCompilationDto dto новой подборки
     */
    @Override
    public CompilationDto addNewCompilation(NewCompilationDto newCompilationDto) {
        Set<Event> events = newCompilationDto.getEvents().stream()
                .map((id) -> eventRepository.findById(id).orElseThrow())
                .collect(Collectors.toSet());
        Compilation newComp = compRepository.save(CompilationMapper.toCompilation(newCompilationDto, events));
        return CompilationMapper.toCompilationDto(newComp);
    }

    /**
     * Удаление подборки
     *
     * @param compId id подборки
     */
    @Override
    public void deleteCompilation(long compId) {
        compRepository.deleteById(compId);
    }

    /**
     * Удаление события из подборки
     *
     * @param compId id подборки
     * @param eventId id события
     */
    @Override
    public void deleteEventFromCompilation(long compId, long eventId) {
        Compilation comp = compRepository.findById(compId).orElseThrow();
        Event deletedEvent = eventRepository.findById(eventId).orElseThrow();
        comp.getEvents().remove(deletedEvent);
        compRepository.save(comp);
    }

    /**
     * Добвалене события в подборку
     *
     * @param compId id подборки
     * @param eventId id события
     */
    @Override
    public void addEventToCompilation(long compId, long eventId) {
        Compilation comp = compRepository.findById(compId).orElseThrow();
        Event addedEvent = eventRepository.findById(eventId).orElseThrow();
        comp.getEvents().add(addedEvent);
        compRepository.save(comp);
    }

    /**
     * Открепить подборку на главной странице
     *
     * @param compId id подборки
     */
    @Override
    public void unpinCompilation(long compId) {
        Compilation comp = compRepository.findById(compId).orElseThrow();
        comp.setPinned(false);
        compRepository.save(comp);
    }

    /**
     * Прикрепить подборку на главной странице
     *
     * @param compId id подборки
     */
    @Override
    public void pinCompilation(long compId) {
        Compilation comp = compRepository.findById(compId).orElseThrow();
        comp.setPinned(true);
        compRepository.save(comp);
    }
}
