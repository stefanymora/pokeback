package dev.api.pokestop.rest;

import dev.api.pokestop.entity.Sale;
import dev.api.pokestop.service.CardServiceImpl;
import dev.api.pokestop.service.SaleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleREST {
    @Autowired
    private SaleServiceImpl saleServiceImpl;

    @GetMapping
    public ResponseEntity<List<Sale>> getAllProducts() {
        List<Sale> sales = saleServiceImpl.getAllSales();
        return ResponseEntity.ok(sales);
    }


    @GetMapping("/{saleName}")
    public ResponseEntity<Sale> getSaleByName(@PathVariable String saleName) {
        Sale sale = saleServiceImpl.getSale(saleName);

        if (sale != null) {
            return ResponseEntity.ok(sale);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PostMapping
    public ResponseEntity<String> newSale (@RequestBody Sale newSale) {
        String saleMessage = saleServiceImpl.saveSale(newSale);
        return ResponseEntity.ok(saleMessage);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSale(@PathVariable String id) {
        String result = saleServiceImpl.deleteSale(id);
        return  ResponseEntity.ok(result);
    }


}
