package com.agibank.analytics.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class SalesmanDataParserServiceTest {

    @InjectMocks
    private SalesmanDataParserService service;

    @Test
    public void shouldReturnEmptyList() {
        Assert.assertTrue(service.parseFrom(null).isEmpty());
    }

    @Test
    public void shouldParseFromStringList() {
        String[] line = {"001", "1234567891234", "Pedro", "50000"};
        var lines = new ArrayList<String[]>();
        lines.add(line);

        var salesman = service.parseFrom(lines).get(0);

        Assert.assertEquals("1234567891234", salesman.getCpf());
        Assert.assertEquals("Pedro", salesman.getName());
        Assert.assertEquals(Float.valueOf("50000"), salesman.getSalary());
    }
}
