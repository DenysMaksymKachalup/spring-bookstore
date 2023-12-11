package com.example.springonlinebookstore.service;

import com.example.springonlinebookstore.dto.BookDto;
import com.example.springonlinebookstore.dto.BookSearchParameters;
import com.example.springonlinebookstore.dto.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto createBookRequestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    BookDto updateById(Long id, CreateBookRequestDto createBookRequestDto);

    List<BookDto> search(BookSearchParameters bookSearchParameters);

    void deleteById(Long id);

    List<BookDto> findAll(Pageable pageable);
}
