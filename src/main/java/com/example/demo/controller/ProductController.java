package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ProductController {

    @Autowired
    private ProductService service;

    // POST /api/items - Create record
    @PostMapping
    public ResponseEntity<Product> createItem(@RequestBody Product product) {
        Product savedProduct = service.createProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    // GET /api/items - Retrieve all records
    @GetMapping
    public ResponseEntity<List<Product>> getAllItems() {
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    // GET /api/items/{id} - Retrieve single record
    @GetMapping("/{id}")
    public ResponseEntity<Product> getItemById(@PathVariable Long id) {
        return service.getProductById(id)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // PUT /api/items/{id} - Update record[cite: 1]
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateItem(@PathVariable Long id, @RequestBody Product product) {
        try {
            Product updatedProduct = service.updateProduct(id, product);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE /api/items/{id} - Delete record[cite: 1]
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        service.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}