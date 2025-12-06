package com.example.inventory.controller;

import com.example.inventory.model.Product;
import com.example.inventory.repository.CategoryRepository;
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
  private final ProductService service;
  private final ProductRepository repo;
  private final CategoryRepository categoryRepo;

  public ProductController(ProductService service, ProductRepository repo, CategoryRepository categoryRepo) {
    this.service = service;
    this.repo = repo;
    this.categoryRepo = categoryRepo;
  }

  @GetMapping
  public List<Product> all() { return service.all(); }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getById(@PathVariable Long id) {
    return service.find(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Product> create(@RequestBody Product p) {
    // if category id present, fetch category reference (simple approach)
    if (p.getCategory() != null && p.getCategory().getId() != null) {
      categoryRepo.findById(p.getCategory().getId()).ifPresent(p::setCategory);
    }
    Product saved = service.create(p);
    return ResponseEntity.ok(saved);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product p) {
    if (p.getCategory() != null && p.getCategory().getId() != null) {
      categoryRepo.findById(p.getCategory().getId()).ifPresent(p::setCategory);
    }
    return ResponseEntity.ok(service.update(id, p));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (!repo.existsById(id)) return ResponseEntity.notFound().build();
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  // small utility endpoint
  @GetMapping("/sku/{sku}")
  public ResponseEntity<Product> getBySku(@PathVariable String sku) {
    return repo.findBySku(sku).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }
}
