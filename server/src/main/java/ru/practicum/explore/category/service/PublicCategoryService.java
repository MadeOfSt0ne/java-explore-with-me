package ru.practicum.explore.category.service;

import ru.practicum.explore.category.dto.CategoryDto;

import java.util.List;

public interface PublicCategoryService {

    /**
     * Получение категории по ее идентификатору
     */
    CategoryDto getCategory(long catId);

    /**
     * Получение списка всех категорий
     */
    List<CategoryDto> getAllCategories(int from, int size);
}
