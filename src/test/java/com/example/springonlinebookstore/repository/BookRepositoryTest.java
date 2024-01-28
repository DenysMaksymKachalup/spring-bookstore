package com.example.springonlinebookstore.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springonlinebookstore.model.Book;
import com.example.springonlinebookstore.repository.books.BookRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"classpath:database/add-categories-to-database.sql",
        "classpath:database/add-books-to-database.sql",
        "classpath:database/add-books-categories-dependencies.sql"})
@Sql(scripts = {"classpath:database/delete-all.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Verify findBooksByCategoryId() method works with correct, return list")
    public void findBooks_byCorrectCategoryId_returnList() {
        Long categoryId = 1L;
        Pageable pageable = PageRequest.of(0,5);
        List<Book> actual = bookRepository.findAllByCategoryId(categoryId, pageable);
        assertThat(actual).hasSize(2);
    }

    @Test
    @DisplayName("Verify findBooksByCategoryId() method works with incorrect, return list")
    public void findBooks_byIncorrectCategoryId_returnEmptyList() {
        Long categoryId = 10L;
        Pageable pageable = PageRequest.of(0,5);
        List<Book> actual = bookRepository.findAllByCategoryId(categoryId, pageable);
        assertThat(actual).isEmpty();
    }
}
