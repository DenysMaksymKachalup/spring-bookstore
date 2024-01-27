package com.example.springonlinebookstore.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springonlinebookstore.dto.book.CreateBookRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
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
        String jsonRequest = objectMapper.writeValueAsString(getCreateBookRequestDto());

        mockMvc.perform(post("/books")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.author", is("Author")))
                .andExpect(jsonPath("$.price", is(1)))
                .andExpect(jsonPath("$.title", is("Title")))
                .andExpect(jsonPath("$.coverImage", is("Image")))
                .andExpect(jsonPath("$.description", is("Description")))
                .andExpect(jsonPath("$.isbn", is("9788869185182")));
    }

    @Test
    @DisplayName("Verify findById() method works, return dto")
    @WithMockUser(username = "admin", roles = "ADMIN")
    @Sql(scripts = {"classpath:database/add-categories-to-database.sql",
            "classpath:database/add-books-to-database.sql",
            "classpath:database/add-books-categories-dependencies.sql"})
    public void getBook_byCorrectId_returnDto() throws Exception {
        long bookId = 1L;
        mockMvc.perform(get("/books/" + bookId))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.author", is("Author")))
                .andExpect(jsonPath("$.price", is(89)))
                .andExpect(jsonPath("$.title", is("Title")))
                .andExpect(jsonPath("$.coverImage", is("Image")))
                .andExpect(jsonPath("$.description", is("Description")))
                .andExpect(jsonPath("$.isbn", is("1")));
    }

    @Test
    @DisplayName("Verify update() method works, return dto")
    @WithMockUser(username = "admin", roles = "ADMIN")
    @Sql(scripts = {"classpath:database/add-categories-to-database.sql",
            "classpath:database/add-books-to-database.sql",
            "classpath:database/add-books-categories-dependencies.sql"})
    public void update_byCorrectId_returnDto() throws Exception {
        CreateBookRequestDto createBookRequestDto = getCreateBookRequestDto();
        createBookRequestDto.setAuthor("new Author");
        createBookRequestDto.setTitle("new Title");

        long bookId = 1L;
        String jsonRequest = objectMapper.writeValueAsString(createBookRequestDto);

        mockMvc.perform(put("/books/" + bookId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("new Author"))
                .andExpect(jsonPath("$.price").value(BigDecimal.valueOf(1).doubleValue()))
                .andExpect(jsonPath("$.title").value("new Title"))
                .andExpect(jsonPath("$.coverImage").value("Image"))
                .andExpect(jsonPath("$.description").value("Description"))
                .andExpect(jsonPath("$.isbn").value("9788869185182"))
                .andReturn();
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

    private CreateBookRequestDto getCreateBookRequestDto() {
        CreateBookRequestDto createBookRequestDto = new CreateBookRequestDto();
        createBookRequestDto.setAuthor("Author");
        createBookRequestDto.setPrice(BigDecimal.valueOf(1));
        createBookRequestDto.setCategories(List.of(1L));
        createBookRequestDto.setTitle("Title");
        createBookRequestDto.setCoverImage("Image");
        createBookRequestDto.setDescription("Description");
        createBookRequestDto.setIsbn("9788869185182");
        return createBookRequestDto;
    }
}
