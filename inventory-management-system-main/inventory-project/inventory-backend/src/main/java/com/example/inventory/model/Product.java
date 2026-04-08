package com.example.inventory.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false)
  private String name;

  @Column(nullable=false, unique=true)
  private String sku;

  private Double price;

  private Integer quantity;

  // simple many-to-one to Category (unidirectional from Product -> Category)
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "category_id")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // minor safety
  private Category category;

  // Getters & Setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getSku() { return sku; }
  public void setSku(String sku) { this.sku = sku; }
  public Double getPrice() { return price; }
  public void setPrice(Double price) { this.price = price; }
  public Integer getQuantity() { return quantity; }
  public void setQuantity(Integer quantity) { this.quantity = quantity; }
  public Category getCategory() { return category; }
  public void setCategory(Category category) { this.category = category; }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Product)) return false;
    Product p = (Product) o;
    return Objects.equals(id, p.id);
  }

  @Override
  public int hashCode() { return Objects.hashCode(id); }
}
