package org.adaschool.api.controller.product;

import org.adaschool.api.exception.ProductNotFoundException;
import org.adaschool.api.repository.product.Product;
import org.adaschool.api.service.product.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/products/")
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(@Autowired ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productsService.save(product);
        URI createdProductUri = URI.create("/v1/products/" + createdProduct.getId());
        return ResponseEntity.created(createdProductUri).body(createdProduct);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> allProducts = productsService.all();
        return ResponseEntity.ok(allProducts);
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") String id) {
        Optional<Product> product = productsService.findById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            throw new ProductNotFoundException(id);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable("id") String id) {
        Optional<Product> existingProduct = productsService.findById(id);
        if (existingProduct.isEmpty()) {

            throw new ProductNotFoundException(id);

        }
        productsService.update(existingProduct.get(), product);
        return ResponseEntity.ok(productsService.save(existingProduct.get()));
    }



    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") String id) {
        Optional<Product> product = productsService.findById(id);
        if (product.isPresent()) {
            productsService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            throw new ProductNotFoundException(id);
        }
    }
}
