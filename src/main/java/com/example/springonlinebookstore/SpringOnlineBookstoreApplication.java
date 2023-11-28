package com.example.springonlinebookstore;

import com.example.springonlinebookstore.model.Book;
import com.example.springonlinebookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.math.BigDecimal;

@SpringBootApplication
public class SpringOnlineBookstoreApplication {

    private final BookService bookService;

    @Autowired
    public SpringOnlineBookstoreApplication(BookService bookService) {
        this.bookService = bookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringOnlineBookstoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = new Book();
            book.setTitle("title");
            book.setAuthor("den");
            book.setDescription("Interesting");
            book.setIsbn("123");
            book.setPrice(BigDecimal.TEN);
            book.setCoverImage("image");

            bookService.save(book);

            System.out.println(bookService.findAll());
        };
    }

}
