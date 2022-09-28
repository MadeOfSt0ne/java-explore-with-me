package ru.practicum.explore.services.client;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explore.mappers.CompilationMapper;
import ru.practicum.explore.models.compilation.Compilation;
import ru.practicum.explore.models.compilation.CompilationRepository;
import ru.practicum.explore.models.compilation.dto.CompilationDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicCompilationServiceImpl implements PublicCompilationService {

    private final CompilationRepository compRepository;

    @Override
    public List<CompilationDto> getCompilations(boolean pinned, int from, int size) {
        Pageable pageable = PageRequest.of(from, size);
        Page<Compilation> compilations = compRepository.findAllByPinned(pageable, pinned);
        return CompilationMapper.toCompilationDto(compilations.toList());
    }

    @Override
    public CompilationDto getCompilation(long compId) {
        Compilation compilation = compRepository.findById(compId).orElseThrow();
        return CompilationMapper.toCompilationDto(compilation);
    }

}
