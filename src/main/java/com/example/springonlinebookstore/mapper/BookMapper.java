package com.example.springonlinebookstore.mapper;

import com.example.springonlinebookstore.config.MapperConfig;
import com.example.springonlinebookstore.dto.book.BookDto;
import com.example.springonlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import com.example.springonlinebookstore.dto.book.CreateBookRequestDto;
import com.example.springonlinebookstore.model.Book;
import com.example.springonlinebookstore.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    @Mapping(target = "categoriesIds", ignore = true)
    BookDto toDto(Book book);

    @Mapping(target = "categories", ignore = true)
    Book toModel(CreateBookRequestDto createBookRequestDto);

    BookDtoWithoutCategoryIds toBookDtoWithoutCategoryIds(Book book);

    @AfterMapping
    default void setCategoriesIds(Book book, @MappingTarget BookDto bookDto) {
        List<Long> longList = book.getCategories().stream()
                .map(Category::getId)
                .toList();
        bookDto.setCategoriesIds(longList);
    }

    @AfterMapping
    default void setCategories(@MappingTarget Book book, CreateBookRequestDto createBookRequestDto) {
        Set<Category> categories = createBookRequestDto.getCategories().stream()
                .map(Category::new)
                .collect(Collectors.toSet());
        book.setCategories(categories);
    }
}
