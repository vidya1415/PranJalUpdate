package com.example.aquabeing.models;

public class cartmodel {

    private String brand;
    private Integer quantity;
    private Integer total_price;
    private String customer_name;
    private String dealer_name;
    private String customer_id;
    private Integer amount;
    private String dealer_id;

    public cartmodel(){}

    public cartmodel(String brand, Integer quantity, Integer total_price, String customername, String dealername, String customer_id, Integer amount, String dealer_id) {
        this.brand = brand;
        this.quantity = quantity;
        this.total_price = total_price;
        this.customer_name = customername;
        this.dealer_name = dealername;
        this.customer_id = customer_id;
        this.amount = amount;
        this.dealer_id = dealer_id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Integer total_price) {
        this.total_price = total_price;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getDealer_name() {
        return dealer_name;
    }

    public void setDealer_name(String dealername) {
        this.dealer_name = dealername;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(String dealer_id) {
        this.dealer_id = dealer_id;
    }
}