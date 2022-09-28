package ru.practicum.explore.controllers.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.services.admin.AdminCategoryService;
import ru.practicum.explore.models.category.dto.CategoryDto;

/**
 * Приватный api для работы с категориями
 */
@Slf4j
@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final AdminCategoryService service;

    /**
     * Добавление новой категории
     */
    @PostMapping
    public CategoryDto addNewCategory(@RequestBody CategoryDto catDto) {
        log.info("ADMIN: save new category {}", catDto);
        return service.addNewCategory(catDto);
    }

    /**
     * Обновление сохраненной категории
     */
    @PatchMapping
    public CategoryDto updateCategory(@RequestBody CategoryDto catDto) {
        log.info("ADMIN: update category={}", catDto);
        return service.updateCategory(catDto);
    }

    /**
     * Удаление категории по идентификатору
     */
    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable int catId) {
        log.info("ADMIN: delete category id={}", catId);
        service.deleteCategory(catId);
    }
}
