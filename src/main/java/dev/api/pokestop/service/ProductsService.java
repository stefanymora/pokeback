package dev.api.pokestop.service;

import dev.api.pokestop.entity.Product;

import java.util.List;

public interface ProductsService {

    String saveProduct(Product product) ;

    Product getProduct(String product);

    String deleteProduct(String product);

    List<Product> getAllProducts();

    String updateProduct(String id, Product updateProduct);

}
