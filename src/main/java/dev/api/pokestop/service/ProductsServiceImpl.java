package dev.api.pokestop.service;

import dev.api.pokestop.DAO.ProductsDAO;
import dev.api.pokestop.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    private ProductsDAO productsDAO;


    @Override
    public String saveProduct(Product product) {
        try {
            return productsDAO.saveProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Product getProduct(String product) {
        try {
            return productsDAO.getProduct(product);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public String deleteProduct(String product) {
        try {
            return productsDAO.deleteProduct(product);
        } catch (Exception e) {
            return "Error al eliminar el producto: " + e.getMessage();
        }
    }

    @Override
    public List<Product> getAllProducts() {
        try {
            return productsDAO.getAll();
        } catch (Exception e) {

            return new ArrayList<>();
        }
    }

    @Override
    public String updateProduct(String id, Product updateProduct) {
        try {
            return productsDAO.updateProduct(id, updateProduct);
        } catch (Exception e) {
            return "Error al actualizar el producto: " + e.getMessage();
        }
    }
}
