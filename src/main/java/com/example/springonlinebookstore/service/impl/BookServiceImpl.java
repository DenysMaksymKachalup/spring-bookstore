package com.example.springonlinebookstore.service.impl;

import com.example.springonlinebookstore.dto.book.BookDto;
import com.example.springonlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import com.example.springonlinebookstore.dto.book.BookSearchParameters;
import com.example.springonlinebookstore.dto.book.CreateBookRequestDto;
import com.example.springonlinebookstore.exception.EntityNotFoundException;
import com.example.springonlinebookstore.mapper.BookMapper;
import com.example.springonlinebookstore.model.Book;
import com.example.springonlinebookstore.repository.books.BookRepository;
import com.example.springonlinebookstore.repository.books.BookSpecificationBuilder;
import com.example.springonlinebookstore.service.BookService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cant find book with id: " + id));
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto updateById(Long id, CreateBookRequestDto createBookRequestDto) {
        BookDto bookById = findById(id);
        Book modelBook = bookMapper.toModel(createBookRequestDto);
        modelBook.setId(bookById.getId());
        return bookMapper.toDto(bookRepository.save(modelBook));
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDto> search(BookSearchParameters bookSearchParameters, Pageable pageable) {
        Specification<Book> specification = specificationBuilder.build(bookSearchParameters);
        return bookRepository.findAll(specification, pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(
            Long categoryId, Pageable pageable) {
        return bookRepository.findAllByCategoryId(categoryId,pageable).stream()
                .map(bookMapper::toBookDtoWithoutCategoryIds)
                .toList();
    }
}
