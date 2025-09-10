package dev.api.pokestop.DTO;

import dev.api.pokestop.enums.Positions;
import dev.api.pokestop.enums.Status;
import lombok.Data;

import java.util.Date;

@Data
public class EmployeeDTO {

    private String id;

    private String name;
    private String phone;
    private String email;
    private Date date_birthday;
    private String username;
    private String url_photo;

    private Date start_date;
    private Date end_date;
    private Positions position;
    private Status status;
    private String comments;



}
