package com.example.springonlinebookstore.service.impl;

import com.example.springonlinebookstore.dto.BookDto;
import com.example.springonlinebookstore.dto.BookSearchParameters;
import com.example.springonlinebookstore.dto.CreateBookRequestDto;
import com.example.springonlinebookstore.exception.EntityNotFoundException;
import com.example.springonlinebookstore.mapper.BookMapper;
import com.example.springonlinebookstore.model.Book;
import com.example.springonlinebookstore.repository.books.BookRepository;
import com.example.springonlinebookstore.repository.books.BookSpecificationBuilder;
import com.example.springonlinebookstore.service.BookService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder specificationBuilder;

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

    @Override
    public BookDto updateById(Long id, CreateBookRequestDto createBookRequestDto) {
        BookDto bookById = findById(id);
        Book modelBook = bookMapper.toModel(createBookRequestDto);
        modelBook.setId(bookById.getId());
        return bookMapper.toDto(bookRepository.save(modelBook));
    }

    @Override
    public List<BookDto> search(BookSearchParameters bookSearchParameters) {
        Specification<Book> specification = specificationBuilder.build(bookSearchParameters);
        return bookRepository.findAll(specification).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
