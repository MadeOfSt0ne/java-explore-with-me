package ru.practicum.explore.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.category.dto.CategoryDto;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
public class PublicCatController {

    private final CategoryService service;

    @GetMapping
    public List<CategoryDto> getAllCategories(@RequestParam(value = "from", required = false, defaultValue = "0") int from,
                                              @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("PUBLIC: get list of categories from={}, size={}", from, size);
        return service.getAllCategories(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategory(@PathVariable int catId) {
        log.info("PUBLIC: get category id={}", catId);
        return service.getCategory(catId);
    }
}
