package ru.practicum.explore.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.category.CategoryDto.CategoryDto;

@Slf4j
@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class AdminCatController {

    private final CategoryService service;

    @PostMapping
    public CategoryDto addNewCategory(@RequestBody CategoryDto catDto) {
        log.info("ADMIN: save new category {}", catDto);
        return service.addNewCategory(catDto);
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@RequestBody CategoryDto catDto, @PathVariable int catId) {
        log.info("ADMIN: update category={}, updated={}", catId, catDto);
        return service.updateCategory(catId, catDto);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable int catId) {
        log.info("ADMIN: delete category id={}", catId);
        service.deleteCategory(catId);
    }
}
