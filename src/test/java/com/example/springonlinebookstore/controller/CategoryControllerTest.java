package com.example.springonlinebookstore.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springonlinebookstore.dto.category.CategoryRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@Sql(scripts = {"classpath:database/delete-categories.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
public class CategoryControllerTest {
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
    public void saveBook_ValidCategoryRequestDto_Success() throws Exception {

        String jsonRequest = objectMapper.writeValueAsString(getCategoryRequestDto());

        mockMvc.perform(post("/categories")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.name",is("Name")))
                .andExpect(jsonPath("$.description",is("Description")));
    }

    @Test
    @DisplayName("Verify findById() method works, return dto")
    @WithMockUser(username = "admin", roles = "ADMIN")
    @Sql(scripts = {"classpath:database/add-categories-to-database.sql"})
    public void getCategory_byCorrectId_returnDto() throws Exception {
        long categoryId = 1L;
        mockMvc.perform(get("/categories/" + categoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Name")))
                .andExpect(jsonPath("$.description",is("Description")));
    }

    @Test
    @DisplayName("Verify update() method works, return dto")
    @WithMockUser(username = "admin", roles = "ADMIN")
    @Sql(scripts = {"classpath:database/add-categories-to-database.sql"})
    public void update_byCorrectId_returnDto() throws Exception {
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto("newName", "newDescription");
        long categoryId = 1L;
        String jsonRequest = objectMapper.writeValueAsString(categoryRequestDto);

        mockMvc.perform(put("/categories/" + categoryId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.name",is("newName")))
                .andExpect(jsonPath("$.description",is("newDescription")));
    }

    @Test
    @DisplayName("Verify save() method works")
    @WithMockUser(username = "admin", roles = "ADMIN")
    @Sql(scripts = {"classpath:database/add-categories-to-database.sql"})
    public void deleteCategories_byCorrectId() throws Exception {
        long categoryId = 1L;
        mockMvc.perform(delete("/categories/" + categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private CategoryRequestDto getCategoryRequestDto() {
        return new CategoryRequestDto(
                "Name", "Description"
        );
    }
}
