package ru.practicum.explore.controllers.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.models.category.dto.CategoryDto;
import ru.practicum.explore.services.client.PublicCategoryService;

import java.util.List;

/**
 * Публичный контроллер для запроса категорий для всех пользователей, в т.ч. неавторизованных
 */
@Slf4j
@RestController
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
public class PublicCategoryController {

    private final PublicCategoryService service;

    /**
     * Получение списка всех категорий
     */
    @GetMapping
    public List<CategoryDto> getAllCategories(@RequestParam(value = "from", required = false, defaultValue = "0") int from,
                                              @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("PUBLIC: get list of categories from={}, size={}", from, size);
        return service.getAllCategories(from, size);
    }

    /**
     * Поиск категории по идентификатору
     */
    @GetMapping("/{catId}")
    public CategoryDto getCategory(@PathVariable int catId) {
        log.info("PUBLIC: get category id={}", catId);
        return service.getCategory(catId);
    }
}
