package com.example.springonlinebookstore.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "shopping_carts")
@Data
@SQLDelete(sql = "UPDATE shopping_carts SET is_deleted=true WHERE id=:id")
@Where(clause = "is_deleted=false")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @MapsId
    private User user;

    @OneToMany(mappedBy = "shoppingCart",cascade = CascadeType.ALL)
    private Set<CartItem> cartItems;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public ShoppingCart() {
    }

    public ShoppingCart(Long id) {
        this.id = id;
    }
}
