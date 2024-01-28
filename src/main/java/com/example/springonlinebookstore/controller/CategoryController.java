package com.example.springonlinebookstore.controller;

import com.example.springonlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import com.example.springonlinebookstore.dto.category.CategoryDto;
import com.example.springonlinebookstore.dto.category.CategoryRequestDto;
import com.example.springonlinebookstore.service.BookService;
import com.example.springonlinebookstore.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "categories", description = "Endpoints for managing categories")
@RestController
@RequestMapping(value = "categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @Operation(summary = "Find all categories.", description = "Find all categories.")
    @GetMapping
    public List<CategoryDto> findAll() {
        return categoryService.findAll();
    }

    @Operation(summary = "Find categories by id.", description = "Find categories by id.")
    @GetMapping("/{id}")
    public CategoryDto findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Save category.",
            description = "Save category. Only ADMIN has access save a category.")
    @PostMapping()
    public CategoryDto save(
            @RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        return categoryService.save(categoryRequestDto);
    }

    @Operation(summary = "Update category.",
            description = "Update category. Only ADMIN has access update a category.")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public CategoryDto update(@PathVariable Long id,
                              @RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        return categoryService.update(id, categoryRequestDto);
    }

    @Operation(summary = "Delete category.",
            description = "Delete category. Only ADMIN has access delete a category.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
    }

    @Operation(summary = "Get all books by category.",
            description = "You can get all books by category.")
    @GetMapping("/{id}/books")
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(
            @PathVariable Long id, Pageable pageable) {
        return bookService.getBooksByCategoryId(id, pageable);
    }
}
