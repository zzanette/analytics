package com.agibank.analytics.service;


import com.agibank.analytics.config.ApplicationPropertiesConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SaleDataParserServiceTest {

    @InjectMocks
    private SaleDataParserService service;

    @Mock
    private ApplicationPropertiesConfig propertiesConfig;

    @Test
    public void shouldReturnEmptyList() {
        Assert.assertTrue(service.parseFrom(null).isEmpty());
    }

    @Test
    public void shouldParseSale() {

        when(propertiesConfig.getItemDelimiter()).thenReturn(",");
        when(propertiesConfig.getPropertyItemDelimiter()).thenReturn("-");

        String[] line = {"003", "10", "[1-10-100]", "Pedro"};
        var lines = new ArrayList<String[]>();
        lines.add(line);

        var sale = service.parseFrom(lines).get(0);
        var item = sale.getItems().get(0);

        Assert.assertEquals(Long.valueOf("10"), sale.getId());
        Assert.assertEquals("Pedro", sale.getSalesmanName());

        Assert.assertEquals(Long.valueOf("1"), item.getId());
        Assert.assertEquals(Integer.valueOf("10"), item.getQuantity());
        Assert.assertEquals(Float.valueOf("100"), item.getPrice());
    }
}
