package dev.api.pokestop.entity;


import lombok.Data;
import java.util.Date;

@Data
public class Event {

    private String id;
    private Date date;
    private String name;
    private String description;



}
