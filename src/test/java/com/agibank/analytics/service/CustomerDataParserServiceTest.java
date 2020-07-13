package com.agibank.analytics.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class CustomerDataParserServiceTest {

  @InjectMocks private CustomerDataParserService service;

  @Test
  public void shouldParseCustomerFromListOfString() {
    String[] line = {"002", "2345675434544345", "Jose da SilvaçRural", "Rural"};
    var lines = new ArrayList<String[]>();
    lines.add(line);
    var customer = service.parseFrom(lines).get(0);

    Assert.assertEquals("2345675434544345", customer.getCnpj());
    Assert.assertEquals("Jose da SilvaçRural", customer.getName());
    Assert.assertEquals("Rural", customer.getBusinessArea());
  }

  @Test
  public void shouldReturnEmptyList() {
    Assert.assertTrue(service.parseFrom(null).isEmpty());
  }
}
