package com.example.inventory.service;

import com.example.inventory.model.Product;
import com.example.inventory.repository.ProductRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
  private final ProductRepository repo;
  private final SimpMessagingTemplate messagingTemplate;

  public ProductService(ProductRepository repo, SimpMessagingTemplate messagingTemplate) {
    this.repo = repo;
    this.messagingTemplate = messagingTemplate;
  }

  public List<Product> all() {
    return repo.findAll();
  }

  public Optional<Product> find(Long id) {
    return repo.findById(id);
  }

  public Product create(Product p) {
    Product saved = repo.save(p);
    // broadcast
    messagingTemplate.convertAndSend("/topic/products", saved);
    return saved;
  }

  public Product update(Long id, Product updated) {
    return repo.findById(id).map(existing -> {
      existing.setName(updated.getName());
      existing.setSku(updated.getSku());
      existing.setPrice(updated.getPrice());
      existing.setQuantity(updated.getQuantity());
      existing.setCategory(updated.getCategory());
      Product saved = repo.save(existing);
      messagingTemplate.convertAndSend("/topic/products", saved);
      return saved;
    }).orElseGet(() -> {
      updated.setId(id);
      Product saved = repo.save(updated);
      messagingTemplate.convertAndSend("/topic/products", saved);
      return saved;
    });
  }

  public void delete(Long id) {
    repo.deleteById(id);
    // broadcast a minimal message to indicate deletion
    messagingTemplate.convertAndSend("/topic/products/delete", id);
  }
}
