package com.example.springonlinebookstore.controller;

import com.example.springonlinebookstore.dto.book.BookDto;
import com.example.springonlinebookstore.dto.book.CreateBookRequestDto;
import com.example.springonlinebookstore.exception.EntityNotFoundException;
import com.example.springonlinebookstore.model.Book;
import com.example.springonlinebookstore.model.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Sql(scripts = {"classpath:database/add-categories-to-database.sql",
        "classpath:database/add-books-to-database.sql",
        "classpath:database/add-books-categories-dependencies.sql"})
@Sql(scripts = {"classpath:database/delete-all.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
public class BookControllerTest {
    protected static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("")
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void saveBook_ValidCreateBookRequestDto_Success() throws Exception {
        CreateBookRequestDto createBookRequestDto = new CreateBookRequestDto();
        createBookRequestDto.setAuthor("Author");
        createBookRequestDto.setPrice(BigDecimal.valueOf(1));
        createBookRequestDto.setCategories(List.of(1L));
        createBookRequestDto.setTitle("Title");
        createBookRequestDto.setCoverImage("Image");
        createBookRequestDto.setDescription("Description");
        createBookRequestDto.setIsbn("9788869185182");

        String jsonRequest = objectMapper.writeValueAsString(createBookRequestDto);

        MvcResult result = mockMvc.perform(post("/books")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsString(), BookDto.class);
        assertNotNull(actual);
        EqualsBuilder.reflectionEquals(getBookDto(), actual, "id");
    }

    @Test
    @DisplayName("")
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void getBook_byCorrectId_returnDto() throws Exception {
        long bookId = 1L;
        MvcResult result = mockMvc.perform(get("/books/" + bookId))
                .andExpect(status().is(200))
                .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsString(), BookDto.class);
        assertNotNull(actual);
        EqualsBuilder.reflectionEquals(getBookDto(), actual, "id");
    }

    @Test
    @DisplayName("")
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void getBook_byIncorrectId_returnDto() throws Exception {
        long bookId = 11L;
        // Використовуємо assertThrows для очікування EntityNotFoundException
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            mockMvc.perform(get("/books/" + bookId))
                    .andExpect(status().isNotFound())
                    .andReturn();
        });
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
