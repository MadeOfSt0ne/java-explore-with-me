package ru.practicum.explore.category.dto;

import lombok.NoArgsConstructor;
import ru.practicum.explore.category.Category;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
public class CategoryMapper {

    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public static Category toCategory(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getName());
    }

    public static List<CategoryDto> toCategoryDto(Collection<Category> categories) {
        List<CategoryDto> dtos = new ArrayList<>();
        for (Category category : categories) {
            dtos.add(toCategoryDto(category));
        }
        return dtos;
    }
}
