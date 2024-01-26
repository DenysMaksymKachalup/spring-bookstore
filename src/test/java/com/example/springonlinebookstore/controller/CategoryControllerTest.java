package com.example.springonlinebookstore.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springonlinebookstore.dto.category.CategoryDto;
import com.example.springonlinebookstore.dto.category.CategoryRequestDto;
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
    @DisplayName("ads")
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void saveBook_ValidCategoryRequestDto_Success() throws Exception {
        CategoryRequestDto categoryRequestDto = getCategoryRequestDto();
        String jsonRequest = objectMapper.writeValueAsString(categoryRequestDto);

        MvcResult result = mockMvc.perform(post("/categories")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andReturn();

        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryDto.class);
        assertNotNull(actual);
        EqualsBuilder.reflectionEquals(getCategoryDto(), actual, "id");
    }

    @Test
    @DisplayName("asd")
    @WithMockUser(username = "admin", roles = "ADMIN")
    @Sql(scripts = {"classpath:database/add-categories-to-database.sql"})
    public void getCategory_byCorrectId_returnDto() throws Exception {
        long categoryId = 2L;
        MvcResult result = mockMvc.perform(get("/categories/" + categoryId))
                .andExpect(status().is(200))
                .andReturn();

        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryDto.class);
        assertNotNull(actual);
        EqualsBuilder.reflectionEquals(getCategoryDto(), actual, "id");
    }

    @Test
    @DisplayName("asdad")
    @WithMockUser(username = "admin", roles = "ADMIN")
    @Sql(scripts = {"classpath:database/add-categories-to-database.sql"})
    public void update_byCorrectId_returnDto() throws Exception {
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto("newName", "newName");
        CategoryDto categoryDto = new CategoryDto(1L, "newName", "newName");

        long categoryId = 1L;
        String jsonRequest = objectMapper.writeValueAsString(categoryRequestDto);

        MvcResult result = mockMvc.perform(put("/categories/" + categoryId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andReturn();

        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryDto.class);
        assertNotNull(actual);
        EqualsBuilder.reflectionEquals(categoryDto, actual, "id");
    }

    @Test
    @DisplayName("asd")
    @WithMockUser(username = "admin", roles = "ADMIN")
    @Sql(scripts = {"classpath:database/add-categories-to-database.sql"})
    public void deleteCategories_byCorrectId() throws Exception {
        long categoryId = 1L;
        mockMvc.perform(delete("/categories/" + categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    private CategoryDto getCategoryDto() {
        return new CategoryDto(1L, "Name", "Description");
    }

    private Category getCategory() {
        Category category = new Category(1L);
        category.setName("Name");
        category.setDescription("Description");
        return category;
    }

    private CategoryRequestDto getCategoryRequestDto() {
        return new CategoryRequestDto(
                "Name", "Description"
        );
    }
}
