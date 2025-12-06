package com.example.inventory.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "categories")
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  // Getters / Setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Category)) return false;
    Category c = (Category) o;
    return Objects.equals(id, c.id);
  }

  @Override
  public int hashCode() { return Objects.hashCode(id); }
}
