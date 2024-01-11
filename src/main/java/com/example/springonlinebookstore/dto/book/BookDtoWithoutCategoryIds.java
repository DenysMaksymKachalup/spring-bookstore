package com.example.springonlinebookstore.dto.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.ISBN;
import java.math.BigDecimal;

public record BookDtoWithoutCategoryIds(
        Long id,
        @NotNull @NotBlank String title,
        @NotNull String author,
        @NotNull @ISBN String isbn,
        @NotNull @Min(0) BigDecimal price,
        @Size(max = 255) String description,
        String coverImage
) {
}
