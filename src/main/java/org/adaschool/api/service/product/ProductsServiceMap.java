package org.adaschool.api.service.product;

import org.adaschool.api.repository.product.Product;
import org.adaschool.api.repository.product.ProductDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductsServiceMap implements ProductsService {

    private final List<Product> products = new ArrayList<>();

    @Override
    public Product save(Product product) {
        String productId = UUID.randomUUID().toString();
        product.setId(productId);
        products.add(product);

        return product;
    }

    @Override
    public Optional<Product> findById(String id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> all() {
        return products;
    }

    @Override
    public void deleteById(String id) {
        products.removeIf(product -> product.getId().equals(id));
    }

    @Override
    public void update(Product oldProduct, Product updatedProduct) {
        ProductDto pdo = new ProductDto(updatedProduct.getName(), updatedProduct.getDescription(), updatedProduct.getCategory(), updatedProduct.getTags(), updatedProduct.getPrice(), updatedProduct.getImageUrl());
        oldProduct.update(pdo);
    }
}

