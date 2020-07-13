package com.agibank.analytics.service;

import com.agibank.analytics.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerDataParserService {

  public List<Customer> parseFrom(List<String[]> customers) {
    if (customers == null) {
      return new ArrayList<>();
    }

    return customers.stream()
        .map(customerLine -> new Customer(customerLine[1], customerLine[2], customerLine[3]))
        .collect(Collectors.toList());
  }
}
