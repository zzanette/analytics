package com.agibank.analytics.domain;


public abstract class DataAnalytics {

    private DataType dataType;

    DataAnalytics(DataType dataType) {
        this.dataType = dataType;
    }
}
