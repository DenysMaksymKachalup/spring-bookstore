package com.example.springonlinebookstore.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

@Data
public class BookDto {
    private Long id;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    private String author;

    @NotNull
    @ISBN
    private String isbn;

    @NotNull
    @Min(1)
    private BigDecimal price;

    @Size(max = 255)
    private String description;

    private String coverImage;

    private List<Long> categoriesIds;
}
