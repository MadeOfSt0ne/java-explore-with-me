package ru.practicum.explore.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    private long id;
    private boolean pinned;
    private String title;
    private List<Long> events;
}
