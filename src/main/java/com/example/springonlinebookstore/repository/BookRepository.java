package com.example.springonlinebookstore.repository;

import com.example.springonlinebookstore.model.Book;
import java.util.List;

public interface BookRepository {
    Book save(Book book);
    List<Book> findAll();
}
