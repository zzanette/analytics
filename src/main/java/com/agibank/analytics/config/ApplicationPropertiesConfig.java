package com.agibank.analytics.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationPropertiesConfig {

  @Value("${app.homepath}")
  private String homepath;

  @Value("${app.delimiters.column}")
  private String columnDelimiter;

  @Value("${app.delimiters.item}")
  private String itemDelimiter;

  @Value("${app.delimiters.property-item}")
  private String propertyItemDelimiter;

  public String getHomepath() {
    return homepath;
  }

  public String getColumnDelimiter() {
    return columnDelimiter;
  }

  public String getItemDelimiter() {
    return itemDelimiter;
  }

  public String getPropertyItemDelimiter() {
    return propertyItemDelimiter;
  }

  public String getPathHomeIn() {
    return getHomepath().concat("/data/in");
  }

  public String getPathHomeOut() {
    return getHomepath().concat("/data/out");
  }
}
