package com.example.springonlinebookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Data
@RequiredArgsConstructor
@SQLDelete(sql = "UPDATE books SET is_deleted = TRUE WHERE id = ?")
@Where(clause = "is_deleted = FALSE")
@Table(name = "books")
public class Book {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private BigDecimal price;

    private String description;

    private String coverImage;

    @Column(nullable = false)
    private boolean isDeleted = false;

    @ManyToMany()
    @JoinTable(name = "books_categories",
            joinColumns = @JoinColumn(name = "books_id"),
            inverseJoinColumns = @JoinColumn(name = "categories_id"))
    private Set<Category> categories = new HashSet<>();

    public Book(Long id) {
        this.id = id;
    }
}
