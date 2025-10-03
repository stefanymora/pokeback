package dev.api.pokestop.rest;

import dev.api.pokestop.entity.Supply;
import dev.api.pokestop.service.SupplyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supply")
public class SupplyREST {

    @Autowired
    private SupplyServiceImpl supplyServiceImpl;


    @GetMapping
    public ResponseEntity<List<Supply>> getAllSupply() {
        List<Supply> supplys = supplyServiceImpl.getAllSupply();
        return ResponseEntity.ok(supplys);
    }


    @GetMapping("/{supply}")
    public ResponseEntity<Supply> getSupplyByName (@PathVariable String supply) {
        Supply supply_1 = supplyServiceImpl.getSupply(supply);

        if (supply_1 != null) {
            return ResponseEntity.ok(supply_1);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PutMapping("/{supply}")
    public ResponseEntity<String> updateSupply (@PathVariable String supply, @RequestBody Supply updateSupply) {
        String result = supplyServiceImpl.updateSupply(supply, updateSupply);

        if (result.startsWith("Insumo actualizado")) {
            return ResponseEntity.ok(result);
        } else if (result.contains("no encontrado")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @PostMapping
    public ResponseEntity<String> newSupply (@RequestBody Supply supply) {
        if (supply.getName() == null || supply.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Nombre del insumo obligatorio.");
        }

        String productMessage = supplyServiceImpl.saveSupply(supply);
        return ResponseEntity.ok(productMessage);
    }


    @DeleteMapping("/{supply}")
    public ResponseEntity<String> deleteSupply (@PathVariable String supply) {
        String result = supplyServiceImpl.deleteSupply(supply);

        if (result.contains("Insumo eliminado correctamente")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

}
