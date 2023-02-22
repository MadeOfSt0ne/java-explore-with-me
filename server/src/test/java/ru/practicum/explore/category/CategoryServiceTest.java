package ru.practicum.explore.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.annotation.DirtiesContext;
import redis.embedded.RedisServer;
import ru.practicum.explore.models.category.dto.CategoryDto;
import ru.practicum.explore.services.admin.AdminCategoryService;
import ru.practicum.explore.services.client.PublicCategoryService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@EnableCaching
@ImportAutoConfiguration(classes = {CacheAutoConfiguration.class, RedisAutoConfiguration.class})
public class CategoryServiceTest {

    private final AdminCategoryService adminService;
    private final PublicCategoryService publicService;
    @Autowired
    CategoryServiceTest(AdminCategoryService adminService, PublicCategoryService publicService) {
        this.adminService = adminService;
        this.publicService = publicService;
    }

    CategoryDto categoryDto;

    CategoryDto updated;

    @BeforeEach
    void setUp() {
        categoryDto = CategoryDto.builder().id(1L).name("Category").build();
        updated = CategoryDto.builder().id(1L).name("New name").build();
    }

    @Test
    void contextLoads() {
        assertNotNull(adminService);
    }

    @Test
    void testAddNewCategory() {
        final CategoryDto catDto = adminService.addNewCategory(categoryDto);
        assertEquals(catDto, categoryDto);
    }

    @Test
    void testUpdateCategory() {
        final CategoryDto catDto = adminService.addNewCategory(categoryDto);
        final CategoryDto catUpdated = adminService.updateCategory(updated);
        assertEquals("New name", catUpdated.getName());
    }

    @Test
    void testDeleteCategory() {
        final CategoryDto catDto = adminService.addNewCategory(categoryDto);
        assertNotNull(catDto);
        adminService.deleteCategory(catDto.getId());
        assertEquals(0, publicService.getAllCategories(0, 10).size());
    }

    @Test
    void testGetCategory() {
        final CategoryDto catDto = adminService.addNewCategory(categoryDto);
        assertEquals(catDto, publicService.getCategory(categoryDto.getId()));
    }

    @Test
    void testGetAllCategories() {
        final CategoryDto catDto = adminService.addNewCategory(categoryDto);
        final CategoryDto catDto1 = adminService.addNewCategory(categoryDto.toBuilder().id(2L).name("name").build());
        assertEquals(List.of(catDto, catDto1), publicService.getAllCategories(0, 10));
    }

    @TestConfiguration
    static class EmbeddedRedisConfiguration {

        private final RedisServer redisServer;

        public EmbeddedRedisConfiguration() {
            this.redisServer = new RedisServer();
        }

        @PostConstruct
        public void startRedis() {
            redisServer.start();
        }

        @PreDestroy
        public void stopRedis() {
            this.redisServer.stop();
        }
    }
}
