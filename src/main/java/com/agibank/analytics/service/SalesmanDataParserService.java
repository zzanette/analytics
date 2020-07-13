package com.agibank.analytics.service;

import com.agibank.analytics.domain.Salesman;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalesmanDataParserService {

  public List<Salesman> parseFrom(List<String[]> salesmen) {
    if (salesmen == null) {
      return new ArrayList<>();
    }

    return salesmen.stream()
        .map(
            (salesmanLine) ->
                new Salesman(salesmanLine[1], salesmanLine[2], Float.valueOf(salesmanLine[3])))
        .collect(Collectors.toList());
  }
}
