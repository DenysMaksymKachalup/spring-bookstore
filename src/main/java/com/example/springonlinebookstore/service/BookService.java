package com.example.springonlinebookstore.service;

import com.example.springonlinebookstore.dto.BookDto;
import com.example.springonlinebookstore.dto.BookSearchParameters;
import com.example.springonlinebookstore.dto.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto createBookRequestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    BookDto updateById(Long id, CreateBookRequestDto createBookRequestDto);

    List<BookDto> search(BookSearchParameters bookSearchParameters);

    void deleteById(Long id);

}
