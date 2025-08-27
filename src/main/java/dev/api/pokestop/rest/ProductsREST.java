package dev.api.pokestop.rest;


import dev.api.pokestop.entity.Product;
import dev.api.pokestop.service.ProductsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")

public class ProductsREST {

    @Autowired
    private ProductsServiceImpl productsServiceImpl;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productsServiceImpl.getAllProducts();
        return ResponseEntity.ok(products);
    }


    @GetMapping("/{product}")
    public ResponseEntity<Product> getProductByName(@PathVariable String product) {
        Product product1 = productsServiceImpl.getProduct(product);

        if (product1 != null) {
            return ResponseEntity.ok(product1);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PutMapping("/{product}")
    public ResponseEntity<String> updateProduct(@PathVariable String product, @RequestBody Product updateProduct) {
        String result = productsServiceImpl.updateProduct(product, updateProduct);

        if (result.startsWith("Producto actualizado")) {
            return ResponseEntity.ok(result);
        } else if (result.contains("no encontrado")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @PostMapping
    public ResponseEntity<String> newProduct(@RequestBody Product productB) {
        if (productB.getProduct() == null || productB.getProduct().isEmpty()) {
            return ResponseEntity.badRequest().body("Nombre del producto obligatorio.");
        }

        String productMessage = productsServiceImpl.saveProduct(productB);
        return ResponseEntity.ok(productMessage);
    }


    @DeleteMapping("/{product}")
    public ResponseEntity<String> deleteProduct(@PathVariable String product) {
        String result = productsServiceImpl.deleteProduct(product);

        if (result.contains("Producto eliminado correctamente")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

}
