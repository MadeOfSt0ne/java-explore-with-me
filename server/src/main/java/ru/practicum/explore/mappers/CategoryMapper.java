package ru.practicum.explore.mappers;

import lombok.NoArgsConstructor;
import ru.practicum.explore.models.category.Category;
import ru.practicum.explore.models.category.dto.CategoryDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Маппер для категорий событий
 */
@NoArgsConstructor
public class CategoryMapper {

    /**
     * Создание дто из категории
     */
    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    /**
     * Создание категории из дто
     */
    public static Category toCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        return category;
    }

    /**
     * Создание списка дто из списка категорий
     */
    public static List<CategoryDto> toCategoryDto(Collection<Category> categories) {
        List<CategoryDto> dtos = new ArrayList<>();
        for (Category category : categories) {
            dtos.add(toCategoryDto(category));
        }
        return dtos;
    }
}
