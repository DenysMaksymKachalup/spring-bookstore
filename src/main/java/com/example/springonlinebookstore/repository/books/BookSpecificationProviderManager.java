package com.example.springonlinebookstore.repository.books;

import com.example.springonlinebookstore.model.Book;
import com.example.springonlinebookstore.repository.SpecificationProvider;
import com.example.springonlinebookstore.repository.SpecificationProviderManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {

    private final List<SpecificationProvider<Book>> bookSpecification;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecification.stream()
                .filter(s -> s.getKey().equals(key))
                .findFirst()
                .orElseThrow();
    }
}
