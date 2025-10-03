package dev.api.pokestop.service;

import dev.api.pokestop.entity.Supply;

import java.util.List;

public interface SupplyService {

    String saveSupply(Supply supply) ;

    Supply getSupply(String supply);

    String deleteSupply(String supply);

    List<Supply> getAllSupply();

    String updateSupply(String id, Supply updateSupply);

}
