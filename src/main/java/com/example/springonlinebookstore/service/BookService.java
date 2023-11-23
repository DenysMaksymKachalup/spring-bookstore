package com.example.springonlinebookstore.service;

import com.example.springonlinebookstore.model.Book;
import java.util.List;

public interface BookService {
    Book save(Book book);
    List<Book> findAll();
}
