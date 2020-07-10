package com.agibank.analytics.domain;

public class Customer extends DataAnalytics {

    private String cnpj;
    private String name;
    private String businessArea;

    public Customer(String cnpj, String name, String businessArea) {
        super(DataType.CUSTOMER);
        this.cnpj = cnpj;
        this.name = name;
        this.businessArea = businessArea;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getName() {
        return name;
    }

    public String getBusinessArea() {
        return businessArea;
    }
}
