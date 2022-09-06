package ru.practicum.explore.category;

import ru.practicum.explore.category.CategoryDto.CategoryDto;

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
     * Поиск категории
     */
     CategoryDto getCategory(long catId);

    /**
     * Получение списка всех категорий
     */
    List<CategoryDto> getAllCategories(int from, int size);
}
