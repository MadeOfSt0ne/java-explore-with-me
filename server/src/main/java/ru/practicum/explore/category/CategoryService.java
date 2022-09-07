package ru.practicum.explore.category;

import ru.practicum.explore.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    /**
     * Добавление новой категории
     */
    CategoryDto addNewCategory(CategoryDto catDto);

    /**
     * Обновление категории
     */
    CategoryDto updateCategory(long catId, CategoryDto catDto);

    /**
     * Удаление категории
     */
    void deleteCategory(long catId);

    /**
     * Получение категории по ее идентификатору
     */
     CategoryDto getCategory(long catId);

    /**
     * Получение списка всех категорий
     */
    List<CategoryDto> getAllCategories(int from, int size);
}
