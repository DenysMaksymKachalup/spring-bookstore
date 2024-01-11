package com.example.springonlinebookstore.repository.books;

import com.example.springonlinebookstore.model.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long>, JpaSpecificationExecutor<Book> {
    List<Book> findAllByCategoriesId(Long id, Pageable pageable);
}
