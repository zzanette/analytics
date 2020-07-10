package com.agibank.analytics.domain;

import java.util.Arrays;

public enum DataType {
    SALESMAN("001"),
    CUSTOMER("002"),
    SALE("003");

    private String typeCode;

    DataType(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public static DataType getBy(String typeCode) {
        return Arrays.stream(values())
                .filter((type) -> type.getTypeCode().equals(typeCode)).findAny()
                .orElse(null);
    }
}
