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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_employee() {
        return id_employee;
    }

    public void setId_employee(String id_employee) {
        this.id_employee = id_employee;
    }

    public Date getDate_hour() {
        return date_hour;
    }

    public void setDate_hour(Date date_hour) {
        this.date_hour = date_hour;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public PaymentMethods getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(PaymentMethods payment_method) {
        this.payment_method = payment_method;
    }
}

