package com.example.springonlinebookstore.service.impl;

import com.example.springonlinebookstore.dto.category.CategoryDto;
import com.example.springonlinebookstore.dto.category.CategoryRequestDto;
import com.example.springonlinebookstore.exception.EntityNotFoundException;
import com.example.springonlinebookstore.mapper.CategoryMapper;
import com.example.springonlinebookstore.model.Category;
import com.example.springonlinebookstore.repository.categories.CategoryRepository;
import com.example.springonlinebookstore.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Category wasn't found!"));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto save(CategoryRequestDto categoryRequestDto) {
        Category savedCategory = categoryRepository
                .save(categoryMapper.toModel(categoryRequestDto));
        return categoryMapper.toDto(savedCategory);
    }

    @Override
    public CategoryDto update(Long id, CategoryRequestDto categoryRequestDto) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Category with id: " + id + "not found");
        }

        Category category = categoryMapper.toModel(categoryRequestDto);
        category.setId(id);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
