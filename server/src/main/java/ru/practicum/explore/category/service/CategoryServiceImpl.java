package ru.practicum.explore.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explore.category.Category;
import ru.practicum.explore.category.CategoryRepository;
import ru.practicum.explore.category.dto.CategoryDto;
import ru.practicum.explore.category.dto.CategoryMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements AdminCategoryService, PublicCategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Добавление новой категории
     *
     * @param catDto dto категории
     */
    @Override
    public CategoryDto addNewCategory(CategoryDto catDto) {
        Category category = categoryRepository.save(CategoryMapper.toCategory(catDto));
        return CategoryMapper.toCategoryDto(category);
    }

    /**
     * Обновление категории
     *
     * @param catDto dto категории
     */
    @Override
    public CategoryDto updateCategory(CategoryDto catDto) {
        Category updated = categoryRepository.findById(catDto.getId()).orElseThrow();
        updated.setName(catDto.getName());
        categoryRepository.save(updated);
        return CategoryMapper.toCategoryDto(updated);
    }

    /**
     * Удаление категории
     *
     * @param catId id категории
     */
    @Override
    public void deleteCategory(long catId) {
        categoryRepository.deleteById(catId);
    }

    /**
     * Поиск категории
     *
     * @param catId id категории
     */
    @Override
    public CategoryDto getCategory(long catId) {
        Category category = categoryRepository.findById(catId).orElseThrow();
        return CategoryMapper.toCategoryDto(category);
    }

    /**
     * Получение списка всех категорий
     *
     * @param from количество категорий, которые нужно пропустить для формирования текущего набора
     * @param size количество категорий в наборе
     */
    @Override
    public List<CategoryDto> getAllCategories(int from, int size) {
        Pageable pageable = PageRequest.of(from, size);
        Page<Category> categories = categoryRepository.findAll(pageable);
        return CategoryMapper.toCategoryDto(categories.toList());
    }
}
