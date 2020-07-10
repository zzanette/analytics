package com.agibank.analytics.domain;

public class Salesman extends DataAnalytics {

    private String cpf;
    private String name;
    private Float salary;

    public Salesman(String cpf, String name, Float salary) {
        super(DataType.SALESMAN);
        this.cpf = cpf;
        this.name = name;
        this.salary = salary;
    }

    public String getCpf() {
        return cpf;
    }

    public String getName() {
        return name;
    }

    public Float getSalary() {
        return salary;
    }
}
