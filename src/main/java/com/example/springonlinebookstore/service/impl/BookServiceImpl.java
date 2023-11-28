package com.example.springonlinebookstore.service.impl;

import com.example.springonlinebookstore.dto.BookDto;
import com.example.springonlinebookstore.dto.CreateBookRequestDto;
import com.example.springonlinebookstore.model.Book;
import com.example.springonlinebookstore.repository.BookRepository;
import com.example.springonlinebookstore.service.BookService;
import com.example.springonlinebookstore.mapper.BookMapper;
import com.example.springonlinebookstore.exception.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto save(CreateBookRequestDto createBookRequestDto) {
        return bookMapper.toDto(bookRepository.save(bookMapper.toModel(createBookRequestDto)));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cant find book by id: " + id));
        return bookMapper.toDto(book);
    }
}
