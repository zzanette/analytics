package com.agibank.analytics.domain;

public class ItemSale {

    private Long id;
    private Integer quantity;
    private Float price;

    public ItemSale(Long id, Integer quantity, Float price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Float getPrice() {
        return price;
    }

    public Float getTotal() {
        return price * quantity;
    }
}
