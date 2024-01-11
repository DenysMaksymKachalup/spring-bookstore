package com.example.springonlinebookstore.controller;

import com.example.springonlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import com.example.springonlinebookstore.dto.category.CategoryDto;
import com.example.springonlinebookstore.dto.category.CategoryRequestDto;
import com.example.springonlinebookstore.model.Book;
import com.example.springonlinebookstore.service.BookService;
import com.example.springonlinebookstore.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value = "categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @GetMapping
    public List<CategoryDto> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public CategoryDto findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public CategoryDto save(
            @RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        return categoryService.save(categoryRequestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}")
    public CategoryDto update(@PathVariable Long id,
                              @RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        return categoryService.update(id, categoryRequestDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
    }

    @GetMapping("/{id}/books")
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(@PathVariable Long id, Pageable pageable) {
        return bookService.getBooksByCategoryId(id, pageable);
    }
}
