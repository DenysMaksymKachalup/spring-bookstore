package com.example.springonlinebookstore.service;

import com.example.springonlinebookstore.dto.BookDto;
import com.example.springonlinebookstore.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto createBookRequestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    BookDto updateById(Long id, CreateBookRequestDto createBookRequestDto);

    void deleteById(Long id);
}
