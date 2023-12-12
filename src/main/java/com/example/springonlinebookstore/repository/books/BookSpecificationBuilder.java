package com.example.springonlinebookstore.repository.books;

import com.example.springonlinebookstore.dto.BookSearchParameters;
import com.example.springonlinebookstore.model.Book;
import com.example.springonlinebookstore.repository.SpecificationBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {

    private final BookSpecificationProviderManager bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters bookSearchParameters) {
        Specification<Book> specification = Specification.where(null);
        if (bookSearchParameters.authors() != null
                && bookSearchParameters.authors().length > 0) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider("author")
                    .getSpecification(bookSearchParameters.authors()));
        }
        if (bookSearchParameters.titles() != null
                && bookSearchParameters.titles().length > 0) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider("title")
                    .getSpecification(bookSearchParameters.titles()));
        }

        return specification;
    }
}
