package com.example.springonlinebookstore.mapper;

import com.example.springonlinebookstore.config.MapperConfig;
import com.example.springonlinebookstore.dto.category.CategoryDto;
import com.example.springonlinebookstore.dto.category.CategoryRequestDto;
import com.example.springonlinebookstore.model.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toModel(CategoryRequestDto categoryRequestDto);

}
