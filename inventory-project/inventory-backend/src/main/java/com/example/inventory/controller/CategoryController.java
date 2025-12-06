package com.example.inventory.controller;

import com.example.inventory.model.Category;
import com.example.inventory.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {
  private final CategoryRepository repo;

  public CategoryController(CategoryRepository repo) { this.repo = repo; }

  @GetMapping
  public List<Category> all() { return repo.findAll(); }

  @PostMapping
  public Category create(@RequestBody Category c) { return repo.save(c); }

  @PutMapping("/{id}")
  public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category c) {
    return repo.findById(id).map(existing -> {
      existing.setName(c.getName());
      repo.save(existing);
      return ResponseEntity.ok(existing);
    }).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (repo.existsById(id)) {
      repo.deleteById(id);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }
}
