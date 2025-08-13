package dev.api.pokestop.entity;


import dev.api.pokestop.enums.PaymentMethods;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Sale {

    private String id;

    private String id_employee;
    private Date date_hour;
    private double total_amount;
    private double subtotal;
    private double discount;
    private PaymentMethods payment_method;


}

