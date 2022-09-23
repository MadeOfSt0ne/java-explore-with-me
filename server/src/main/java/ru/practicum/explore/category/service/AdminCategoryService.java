package ru.practicum.explore.category.service;

import ru.practicum.explore.category.dto.CategoryDto;

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
     * Удаление категории
     */
    void deleteCategory(long catId);


}
