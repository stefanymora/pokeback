package dev.api.pokestop.service;

import dev.api.pokestop.DAO.SupplyDAO;
import dev.api.pokestop.entity.Supply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplyServiceImpl implements SupplyService {

    @Autowired
    private SupplyDAO supplyDAO;

    @Override
    public String saveSupply(Supply supply) {
        try {
            return supplyDAO.saveSupply(supply);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Supply getSupply(String supply) {
        try {
            return supplyDAO.getSupply(supply);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String deleteSupply(String supply) {
        try {
            return supplyDAO.deleteSupply(supply);
        } catch (Exception e) {
            return "Error al eliminar el insumo: " + e.getMessage();
        }
    }

    @Override
    public List<Supply> getAllSupply() {
        try {
            return supplyDAO.getAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public String updateSupply(String id, Supply updateSupply) {
        try {
            return supplyDAO.updateSupply(id, updateSupply);
        } catch (Exception e) {
            return "Error al actualizar el insumo: " + e.getMessage();
        }    }
}
