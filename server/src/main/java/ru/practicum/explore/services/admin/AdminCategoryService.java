package ru.practicum.explore.services.admin;

import ru.practicum.explore.models.category.dto.CategoryDto;

/**
 * Сервис для работы с категориями
 */
public interface AdminCategoryService {

    /**
     * Добавление новой категории
     */
    CategoryDto addNewCategory(CategoryDto catDto);

    /**
     * Обновление категории
     */
    CategoryDto updateCategory(CategoryDto catDto);

    /**
     * Удаление категории по идентификатору
     */
    void deleteCategory(long catId);


}
