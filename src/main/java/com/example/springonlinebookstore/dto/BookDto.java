package com.example.springonlinebookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class BookDto {

    private Long id;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    private String author;

    @NotNull
    @Min(0)
    private String isbn;

    @Min(0)
    private BigDecimal price;

    private String description;

    private String coverImage;

}
