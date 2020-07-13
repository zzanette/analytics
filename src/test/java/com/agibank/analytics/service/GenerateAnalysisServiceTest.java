package com.agibank.analytics.service;

import com.agibank.analytics.domain.Customer;
import com.agibank.analytics.domain.Sale;
import com.agibank.analytics.domain.Salesman;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class GenerateAnalysisServiceTest {

  @InjectMocks private GenerateAnalysisService service;

  @Test
  public void shouldGenerateAnalysis() {
    var customers = Collections.singletonList(new Customer("123", "Test", "TestBus"));
    var sales = Collections.singletonList(new Sale(1L, new ArrayList<>(), "Test"));
    var salesmen = Collections.singletonList(new Salesman("123", "Test", Float.valueOf("12")));

    Assert.assertFalse(service.generateLineAnalyses(customers, sales, salesmen).isEmpty());
  }
}
