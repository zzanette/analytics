package com.agibank.analytics.domain;

import lombok.Getter;

@Getter
public abstract class DataAnalytics {

    private DataType dataType;

    DataAnalytics(DataType dataType) {
        this.dataType = dataType;
    }
}
