package ru.practicum.explore.services.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explore.mappers.CategoryMapper;
import ru.practicum.explore.models.category.Category;
import ru.practicum.explore.repository.CategoryRepository;
import ru.practicum.explore.models.category.dto.CategoryDto;
import ru.practicum.explore.services.client.PublicCategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicCategoryServiceImpl implements PublicCategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto getCategory(long catId) {
        Category category = categoryRepository.findById(catId).orElseThrow();
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories(int from, int size) {
        Pageable pageable = PageRequest.of(from, size);
        Page<Category> categories = categoryRepository.findAll(pageable);
        return CategoryMapper.toCategoryDto(categories.toList());
    }
}
