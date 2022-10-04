package ru.practicum.explore.services.client;

import ru.practicum.explore.models.category.dto.CategoryDto;

import java.util.List;

/**
 * Сервис для работы с категориями
 */
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
