package com.agibank.analytics.service;

import com.agibank.analytics.config.ApplicationPropertiesConfig;
import com.agibank.analytics.domain.ItemSale;
import com.agibank.analytics.domain.Sale;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleDataParserService {

  private ApplicationPropertiesConfig propertiesConfig;

  public SaleDataParserService(ApplicationPropertiesConfig propertiesConfig) {
    this.propertiesConfig = propertiesConfig;
  }

  public List<Sale> parseSaleFrom(List<String[]> sales) {
    if (sales == null) {
      return new ArrayList<>();
    }

    return sales.stream()
        .map((saleLine) -> new Sale(Long.parseLong(saleLine[1]), parseItems(saleLine[2]), saleLine[3]))
        .collect(Collectors.toList());
  }

  private List<ItemSale> parseItems(String fileItems) {
    fileItems = fileItems.replace("[", "").replace("]", "");
    var splitedItems = fileItems.split(propertiesConfig.getItemDelimiter());
    var items = new ArrayList<ItemSale>();
    Arrays.stream(splitedItems)
        .forEach(
            (item) -> {
              var propertyItem = item.split(propertiesConfig.getPropertyItemDelimiter());
              items.add(
                  new ItemSale(
                      Long.parseLong(propertyItem[0]),
                      Integer.parseInt(propertyItem[1]),
                      Float.parseFloat(propertyItem[2])));
            });

    return items;
  }
}
