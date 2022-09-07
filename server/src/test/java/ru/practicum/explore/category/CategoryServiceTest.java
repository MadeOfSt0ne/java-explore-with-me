package ru.practicum.explore.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.explore.category.dto.CategoryDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CategoryServiceTest {

    private final CategoryService service;

    @Autowired
    CategoryServiceTest(CategoryService service) {
        this.service = service;
    }

    private CategoryDto categoryDto;

    private CategoryDto updated;

    @BeforeEach
    void setUp() {
        categoryDto = CategoryDto.builder().id(1L).name("Category").build();
        updated = CategoryDto.builder().id(1L).name("New name").build();
    }

    @Test
    void contextLoads() {
        assertNotNull(service);
    }

    @Test
    void testAddNewCategory() {
        final CategoryDto catDto = service.addNewCategory(categoryDto);
        assertEquals(catDto, categoryDto);
    }

    @Test
    void testUpdateCategory() {
        final CategoryDto catDto = service.addNewCategory(categoryDto);
        final CategoryDto catUpdated = service.updateCategory(catDto.getId(), updated);
        assertEquals("New name", catUpdated.getName());
    }

    @Test
    void testDeleteCategory() {
        final CategoryDto catDto = service.addNewCategory(categoryDto);
        assertNotNull(catDto);
        service.deleteCategory(catDto.getId());
        assertEquals(0, service.getAllCategories(0, 10).size());
    }

    @Test
    void testGetCategory() {
        final CategoryDto catDto = service.addNewCategory(categoryDto);
        assertEquals(catDto, service.getCategory(categoryDto.getId()));
    }

    @Test
    void testGetAllCategories() {
        final CategoryDto catDto = service.addNewCategory(categoryDto);
        final CategoryDto catDto1 = service.addNewCategory(categoryDto.toBuilder().id(2L).name("name").build());
        assertEquals(List.of(catDto, catDto1), service.getAllCategories(0, 10));
    }
}
