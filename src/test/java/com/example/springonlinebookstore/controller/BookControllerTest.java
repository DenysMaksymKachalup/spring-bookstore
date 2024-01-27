package com.example.springonlinebookstore.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springonlinebookstore.dto.book.BookDto;
import com.example.springonlinebookstore.dto.book.CreateBookRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
    @DisplayName("Verify save() method works, return dto")
    @WithMockUser(username = "admin", roles = "ADMIN")
    @Sql(scripts = {"classpath:database/add-categories-to-database.sql"})
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

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);
        assertNotNull(actual);
        EqualsBuilder.reflectionEquals(getBookDto(), actual, "id");
    }

    @Test
    @DisplayName("Verify findById() method works, return dto")
    @WithMockUser(username = "admin", roles = "ADMIN")
    @Sql(scripts = {"classpath:database/add-categories-to-database.sql",
            "classpath:database/add-books-to-database.sql",
            "classpath:database/add-books-categories-dependencies.sql"})
    public void getBook_byCorrectId_returnDto() throws Exception {
        long bookId = 2L;
        MvcResult result = mockMvc.perform(get("/books/" + bookId))
                .andExpect(status().is(200))
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);
        assertNotNull(actual);
        EqualsBuilder.reflectionEquals(getBookDto(), actual, "id");
    }

    @Test
    @DisplayName("Verify update() method works, return dto")
    @WithMockUser(username = "admin", roles = "ADMIN")
    @Sql(scripts = {"classpath:database/add-categories-to-database.sql",
            "classpath:database/add-books-to-database.sql",
            "classpath:database/add-books-categories-dependencies.sql"})
    public void update_byCorrectId_returnDto() throws Exception {
        CreateBookRequestDto createBookRequestDto = new CreateBookRequestDto();
        createBookRequestDto.setAuthor("new Author");
        createBookRequestDto.setPrice(BigDecimal.valueOf(1));
        createBookRequestDto.setCategories(List.of(1L));
        createBookRequestDto.setTitle("new Title");
        createBookRequestDto.setCoverImage("Image");
        createBookRequestDto.setDescription("Description");
        createBookRequestDto.setIsbn("9788869185182");
        BookDto bookDto = getBookDto();
        bookDto.setAuthor("new Author");
        bookDto.setTitle("new Title");

        long bookId = 1L;
        String jsonRequest = objectMapper.writeValueAsString(createBookRequestDto);

        MvcResult result = mockMvc.perform(put("/books/" + bookId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);
        assertNotNull(actual);
        EqualsBuilder.reflectionEquals(bookDto, actual, "id");
    }

    @Test
    @DisplayName("Verify delete() method works")
    @WithMockUser(username = "admin", roles = "ADMIN")
    @Sql(scripts = {"classpath:database/add-categories-to-database.sql",
            "classpath:database/add-books-to-database.sql",
            "classpath:database/add-books-categories-dependencies.sql"})
    public void deleteBook_byCorrectId() throws Exception {
        long bookId = 1L;
        mockMvc.perform(delete("/books/" + bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
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
