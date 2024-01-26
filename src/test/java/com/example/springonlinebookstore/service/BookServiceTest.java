package com.example.springonlinebookstore.service;

import com.example.springonlinebookstore.dto.book.BookDto;
import com.example.springonlinebookstore.dto.book.CreateBookRequestDto;
import com.example.springonlinebookstore.exception.EntityNotFoundException;
import com.example.springonlinebookstore.mapper.BookMapper;
import com.example.springonlinebookstore.model.Book;
import com.example.springonlinebookstore.model.Category;
import com.example.springonlinebookstore.repository.books.BookRepository;
import com.example.springonlinebookstore.service.impl.BookServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Verify save() method works")
    public void save_validCreateBookRequestDto_returnBookDto() {
        CreateBookRequestDto createBookRequestDto = new CreateBookRequestDto();
        createBookRequestDto.setAuthor("Author");
        createBookRequestDto.setPrice(BigDecimal.valueOf(1));
        createBookRequestDto.setCategories(List.of(1L));
        createBookRequestDto.setTitle("Title");
        createBookRequestDto.setCoverImage("Image");
        createBookRequestDto.setDescription("Description");
        createBookRequestDto.setIsbn("9788869185182");

        Book book = getBook();
        BookDto bookDto = getBookDto();

        Mockito.when(bookMapper.toModel(createBookRequestDto)).thenReturn(book);
        Mockito.when(bookMapper.toDto(book)).thenReturn(bookDto);
        Mockito.when(bookRepository.save(book)).thenReturn(book);

        BookDto actual = bookService.save(createBookRequestDto);

        assertEquals(actual,bookDto);
    }

    @Test
    @DisplayName("Verify findAll() with books, method works")
    public void findAll_withBook_returnBookDtoList() {
        Book book = getBook();
        Pageable pageable = PageRequest.of(0,1);
        BookDto bookDto = getBookDto();
        Page<Book> bookPage = new PageImpl<>(List.of(book), pageable, 1);
        Mockito.when(bookMapper.toDto(book)).thenReturn(bookDto);
        Mockito.when(bookRepository.findAll(pageable)).thenReturn(bookPage);

        List<BookDto> actual = bookService.findAll(pageable);

        assertEquals(List.of(bookDto),actual);
    }


    @Test
    @DisplayName("Verify findById() method works")
    public void getBook_byCorrectId_returnBookDto() {
        Long bookId = 1L;
        Book book = getBook();
        BookDto bookDto = getBookDto();

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Mockito.when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto actual = bookService.findById(bookId);

        assertEquals(actual,bookDto);
    }

    @Test
    @DisplayName("Verify findById() with incorrect id, method throw exception")
    public void getBook_byIncorrectId_throwException() {
        Long bookId = 10L;
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,() -> bookService.findById(bookId));
    }

    @Test
    @DisplayName("Verify updateById() with correct id, method return BookDto")
    public void updateBook_byCorrectId_returnUpdatedBookDto() {
        CreateBookRequestDto createBookRequestDto = new CreateBookRequestDto();
        createBookRequestDto.setAuthor("new Author");
        createBookRequestDto.setPrice(BigDecimal.valueOf(1));
        createBookRequestDto.setCategories(List.of(1L));
        createBookRequestDto.setTitle("Title");
        createBookRequestDto.setCoverImage("Image");
        createBookRequestDto.setDescription("Description");
        createBookRequestDto.setIsbn("9788869185182");

        Long bookId = 1L;
        Book book = getBook();

        Book bookForUpdating = getBook();
        bookForUpdating.setAuthor("new Author");

        BookDto bookDto = getBookDto();
        bookDto.setAuthor("new Author");

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Mockito.when(bookService.findById(bookId)).thenReturn(getBookDto());
        Mockito.when(bookMapper.toModel(createBookRequestDto)).thenReturn(bookForUpdating);
        BookDto actual = bookService.updateById(bookId,createBookRequestDto);

        assertEquals(bookDto,actual);
        Mockito.verify(bookService,Mockito.times(1)).findById(bookId);
    }

    @Test
    @DisplayName("Verify deleteById() with correct id, variable isDelete has to be true")
    public void deleteBook_byIdWithCorrectId() {
        Long bookId = 1L;
        Mockito.doNothing().when(bookRepository).deleteById(bookId);
        bookService.deleteById(bookId);
        Mockito.verify(bookRepository,Mockito.times(1)).deleteById(bookId);

    }

    private Book getBook() {
        Category category = new Category();
        category.setDescription("Category");
        category.setId(1L);
        category.setName("Category");

        Book book = new Book(1L);
        book.setAuthor("Author");
        book.setCategories(Set.of(category));
        book.setPrice(BigDecimal.valueOf(1));
        book.setTitle("Title");
        book.setCoverImage("Image");
        book.setDescription("Description");
        book.setIsbn("9788869185182");

        return book;
    }

    private BookDto getBookDto() {
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setAuthor("Author");
        bookDto.setCategoriesIds(List.of(1L));
        bookDto.setPrice(BigDecimal.valueOf(1));
        bookDto.setTitle("Title");
        bookDto.setCoverImage("Image");
        bookDto.setDescription("Description");
        bookDto.setIsbn("9788869185182");
        return bookDto;
    }
}
