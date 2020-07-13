package com.agibank.analytics.service;

import com.agibank.analytics.domain.Customer;
import com.agibank.analytics.domain.Sale;
import com.agibank.analytics.domain.Salesman;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GenerateAnalysisService {

  public List<String> generateLineAnalyses(List<Customer> customers, List<Sale> sales, List<Salesman> salesmen) {
    var lines = new ArrayList<String>();
    generateCustomerAnalysis(lines, customers);
    generateSalesmanAnalysiis(lines, salesmen);
    generateSalesAnalysis(lines, sales);

    return lines;
  }

  private void generateCustomerAnalysis(List<String> lines, List<Customer> customers) {
    Integer customerQuanity = customers == null ? 0 : customers.size();
    lines.add("Quantity customers: " + customerQuanity);
  }

  private void generateSalesmanAnalysiis(List<String> lines, List<Salesman> salesmen) {
    var salesmanQuantity = salesmen == null ? 0 : salesmen.size();
    lines.add("Salesman quantity: " + salesmanQuantity);
  }

  private void generateSalesAnalysis(List<String> lines, List<Sale> sales) {
    var salesmanOptional = sales.stream().max(Comparator.comparing(Sale::getTotalSale));
    salesmanOptional.ifPresent(sale -> lines.add("Most expensive sale: " + sale.getId()));

    Map<String, Float> salesPerSaleman = new HashMap<>();
    sales
        .forEach(
            sale -> {
              if (salesPerSaleman.get(sale.getSalesmanName()) == null) {
                salesPerSaleman.put(sale.getSalesmanName(), Float.valueOf("0"));
              }
              var totalSale = salesPerSaleman.get(sale.getSalesmanName()) + sale.getTotalSale();
              salesPerSaleman.put(sale.getSalesmanName(), totalSale);
            });
    var worstSeller =
        salesPerSaleman.entrySet().stream().min(Map.Entry.comparingByValue());
    worstSeller.ifPresent(sale -> lines.add("Worst seller: " + sale.getKey()));
  }
}
