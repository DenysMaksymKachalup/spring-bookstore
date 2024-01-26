package com.example.springonlinebookstore.service;

import com.example.springonlinebookstore.dto.category.CategoryDto;
import com.example.springonlinebookstore.dto.category.CategoryRequestDto;
import com.example.springonlinebookstore.exception.EntityNotFoundException;
import com.example.springonlinebookstore.mapper.CategoryMapper;
import com.example.springonlinebookstore.model.Category;
import com.example.springonlinebookstore.repository.categories.CategoryRepository;
import com.example.springonlinebookstore.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryMapper categoryMapper;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Verify save() method works")
    public void save_validCategoryRequestDto_returnCategoryDto() {
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto(
                "Name", "Description"
        );
        Category category = new Category(1L);
        CategoryDto categoryDto = new CategoryDto(
                1L, "Name", "Description"
        );

        Mockito.when(categoryMapper.toModel(categoryRequestDto)).thenReturn(category);
        Mockito.when(categoryMapper.toDto(category)).thenReturn(categoryDto);
        Mockito.when(categoryRepository.save(category)).thenReturn(category);

        CategoryDto actual = categoryService.save(categoryRequestDto);

        assertEquals(categoryDto, actual);
    }

    @Test
    @DisplayName("Verify findById() method works")
    public void getCategory_byCorrectId_returnCategoryDto() {
        Long categoryId = 1L;
        Category category = getCategory();
        CategoryDto categoryDto = getCategoryDto();
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        Mockito.when(categoryMapper.toDto(category)).thenReturn(categoryDto);
        CategoryDto actual = categoryService.findById(categoryId);
        assertEquals(categoryDto, actual);
    }

    @Test
    @DisplayName("Verify findById() method throw exception")
    public void getCategory_byIncorrectId_throwException() {
        Long categoryId = 10L;
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,() -> categoryService.findById(categoryId));
    }

    @Test
    @DisplayName("Verify update() method works")
    public void updateCategory_byCorrectId_returnCategory() {
        Long categoryId = 1L;
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto(
                "Name", "Description"
        );
        CategoryDto categoryDto = new CategoryDto(1L,"Name","new Description");
        Category newCategory = getCategory();
        newCategory.setDescription("new Description");

        Mockito.when(categoryRepository.existsById(categoryId)).thenReturn(true);
        Mockito.when(categoryMapper.toModel(categoryRequestDto)).thenReturn(newCategory);
        Mockito.when(categoryRepository.save(newCategory)).thenReturn(newCategory);
        Mockito.when(categoryMapper.toDto(newCategory)).thenReturn(categoryDto);

        CategoryDto actual = categoryService.update(categoryId, categoryRequestDto);

        assertEquals(categoryDto,actual);
    }

    @Test
    @DisplayName("Verify update() method throw exception")
    public void updateCategory_byIncorrectId_throwException() {
        Long categoryId = 11L;
        Mockito.when(categoryRepository.existsById(categoryId)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> categoryService.update(categoryId,getCategoryRequestDto()));
    }

    @Test
    @DisplayName("Verify deleteById() method works")
    public void deleteBook_byIdWithCorrectId() {
        Long categoryId = 1L;
        Mockito.doNothing().when(categoryRepository).deleteById(categoryId);
        categoryService.deleteById(categoryId);
        Mockito.verify(categoryRepository,Mockito.times(1)).deleteById(categoryId);

    }

    private CategoryDto getCategoryDto() {
        return new CategoryDto(1L, "Name", "Description");
    }

    private Category getCategory() {
        Category category = new Category(1L);
        category.setName("Name");
        category.setDescription("Description");
        return category;
    }

    private CategoryRequestDto getCategoryRequestDto() {
        return new CategoryRequestDto(
                "Name", "Description"
        );
    }
}
