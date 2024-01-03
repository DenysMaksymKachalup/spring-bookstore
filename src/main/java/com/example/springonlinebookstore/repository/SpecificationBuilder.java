package com.example.springonlinebookstore.repository;

import com.example.springonlinebookstore.dto.book.BookSearchParameters;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(BookSearchParameters bookSearchParameters);
}
