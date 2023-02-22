package ru.practicum.explore.services.admin.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import ru.practicum.explore.mappers.CategoryMapper;
import ru.practicum.explore.models.category.Category;
import ru.practicum.explore.models.category.dto.CategoryDto;
import ru.practicum.explore.repository.CategoryRepository;
import ru.practicum.explore.repository.EventRepository;
import ru.practicum.explore.services.admin.AdminCategoryService;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "categories")
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public CategoryDto addNewCategory(CategoryDto catDto) {
        Category category = categoryRepository.save(CategoryMapper.toCategory(catDto));
        return CategoryMapper.toCategoryDto(category);
    }

    @CachePut(key = "#catDto.id")
    @Override
    public CategoryDto updateCategory(CategoryDto catDto) {
        Category updated = categoryRepository.findById(catDto.getId()).orElseThrow();
        updated.setName(catDto.getName());
        categoryRepository.save(updated);
        return CategoryMapper.toCategoryDto(updated);
    }

    @CacheEvict(key = "#catId")
    @Override
    public void deleteCategory(long catId) {
        if (eventRepository.findAllByCategoryId(catId).size() != 0) {
            throw new IllegalStateException();
        }
        categoryRepository.deleteById(catId);
    }
}
