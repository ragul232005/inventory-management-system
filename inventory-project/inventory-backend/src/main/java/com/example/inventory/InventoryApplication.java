package com.example.inventory;

import com.example.inventory.model.Category;
import com.example.inventory.model.Product;
import com.example.inventory.repository.CategoryRepository;
import com.example.inventory.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryApplication {
  public static void main(String[] args) {
    SpringApplication.run(InventoryApplication.class, args);
  }

  // seed some data for beginners
  @Bean
  CommandLineRunner seed(CategoryRepository catRepo, ProductRepository prodRepo) {
    return args -> {
      if (catRepo.count() == 0) {
        Category electronics = new Category();
        electronics.setName("Electronics");
        catRepo.save(electronics);

        Category groceries = new Category();
        groceries.setName("Groceries");
        catRepo.save(groceries);

        Product p1 = new Product();
        p1.setName("USB Cable");
        p1.setSku("USB-001");
        p1.setPrice(199.0);
        p1.setQuantity(50);
        p1.setCategory(electronics);
        prodRepo.save(p1);

        Product p2 = new Product();
        p2.setName("Rice (1kg)");
        p2.setSku("GRO-101");
        p2.setPrice(60.0);
        p2.setQuantity(200);
        p2.setCategory(groceries);
        prodRepo.save(p2);
      }
    };
  }
}
