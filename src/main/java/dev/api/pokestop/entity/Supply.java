package dev.api.pokestop.entity;


import lombok.Data;

@Data
public class Supply {

    private String id;
    private String name;
    private int quantity;
    private double cost;
    private String description;

}
