package com.agibank.analytics.domain;

import java.util.List;

public class Sale extends DataAnalytics {

    private Long id;
    private List<ItemSale> items;
    private String salesmanName;

    public Sale(Long id, List<ItemSale> items, String salesmanName) {
        super(DataType.SALE);
        this.id = id;
        this.items = items;
        this.salesmanName = salesmanName;
    }

    public Long getId() {
        return id;
    }

    public List<ItemSale> getItems() {
        return items;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public Float getTotalSale() {
        var total = (float) 0;
        for (ItemSale item : items) {
            total += item.getTotal();
        }

        return total;
    }
}
