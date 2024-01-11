package com.example.springonlinebookstore.service;

import com.example.springonlinebookstore.dto.category.CategoryDto;
import com.example.springonlinebookstore.dto.category.CategoryRequestDto;
import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAll();
    CategoryDto findById(Long id);
    CategoryDto save(CategoryRequestDto categoryRequestDto);
    CategoryDto update(Long id, CategoryRequestDto categoryRequestDto);
    void deleteById(Long id);
}
