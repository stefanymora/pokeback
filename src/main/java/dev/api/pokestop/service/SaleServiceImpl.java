package dev.api.pokestop.service;

import dev.api.pokestop.DAO.EmployeeDAO;
import dev.api.pokestop.DAO.SaleDAO;
import dev.api.pokestop.entity.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SaleServiceImpl implements SaleService{

    @Autowired
    private SaleDAO saleDAO;

    @Override
    public String saveSale(Sale sale) {
       try{
           return saleDAO.saveSale(sale);
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
    }

    @Override
    public Sale getSale(String saleID) {
        try{
            return saleDAO.getSale(saleID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String deleteSale(String saleID) {
        try{
            return saleDAO.deleteSale(saleID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Sale> getAllSales() {
        try{
            return saleDAO.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
