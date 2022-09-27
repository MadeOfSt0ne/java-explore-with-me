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
import ru.practicum.explore.event.EventRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements AdminCategoryService, PublicCategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

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
     * @throws IllegalStateException если с категорией связано хотя бы одно событие
     */
    @Override
    public void deleteCategory(long catId) {
        if (eventRepository.findAllByCategoryId(catId).size() != 0) {
            throw new IllegalStateException();
        }
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
