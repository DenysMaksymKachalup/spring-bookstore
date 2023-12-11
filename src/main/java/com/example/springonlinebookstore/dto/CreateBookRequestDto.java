package com.example.springonlinebookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    @Min(0)
    private String isbn;

    @Min(0)
    private BigDecimal price;

    private String description;

    private String coverImage;
}
