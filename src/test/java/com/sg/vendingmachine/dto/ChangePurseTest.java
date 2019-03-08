/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dto;

import java.math.BigDecimal;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author MadelineHebel
 */
public class ChangePurseTest {

    @Test
    public void testChangePurse150() {

        ChangePurse change = new ChangePurse(new BigDecimal("1.50"));

        int numPennies = change.getNumPennies();

        Assert.assertEquals(0, change.getNumPennies());
        Assert.assertEquals(0, change.getNumNickels());
        Assert.assertEquals(0, change.getNumDimes());
        Assert.assertEquals(6, change.getNumQuarters());
    }

    @Test
    public void testChangePurse237() {

        ChangePurse change = new ChangePurse(new BigDecimal("2.37"));

        int numPennies = change.getNumPennies();

        Assert.assertEquals(change.getNumPennies(), 2);
        Assert.assertEquals(change.getNumNickels(), 0);
        Assert.assertEquals(change.getNumDimes(), 1);
        Assert.assertEquals(change.getNumQuarters(), 9);
    }

}
