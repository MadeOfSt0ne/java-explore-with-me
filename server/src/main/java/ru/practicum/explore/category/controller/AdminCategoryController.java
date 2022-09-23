package ru.practicum.explore.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.category.service.AdminCategoryService;
import ru.practicum.explore.category.dto.CategoryDto;

@Slf4j
@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final AdminCategoryService service;

    @PostMapping
    public CategoryDto addNewCategory(@RequestBody CategoryDto catDto) {
        log.info("ADMIN: save new category {}", catDto);
        return service.addNewCategory(catDto);
    }

    @PatchMapping
    public CategoryDto updateCategory(@RequestBody CategoryDto catDto) {
        log.info("ADMIN: update category={}", catDto);
        return service.updateCategory(catDto);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable int catId) {
        log.info("ADMIN: delete category id={}", catId);
        service.deleteCategory(catId);
    }
}
