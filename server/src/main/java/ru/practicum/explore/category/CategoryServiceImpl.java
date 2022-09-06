package ru.practicum.explore.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explore.category.CategoryDto.CategoryDto;
import ru.practicum.explore.category.CategoryDto.CategoryMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

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
     * @param catId id категории
     * @param catDto dto категории
     */
    @Override
    public CategoryDto updateCategory(long catId, CategoryDto catDto) {
        Category updated = categoryRepository.findById(catId).orElseThrow();
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
        return categories.stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }
}
