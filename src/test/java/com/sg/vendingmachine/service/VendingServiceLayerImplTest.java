/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingDao;
import com.sg.vendingmachine.dao.VendingPersistenceException;
import com.sg.vendingmachine.dto.ChangePurse;
import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.service.exceptions.VendingInsufficientFundsException;
import com.sg.vendingmachine.service.exceptions.VendingNoItemInventoryException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author MadelineHebel
 */
public class VendingServiceLayerImplTest {

    VendingServiceLayerImpl testService;

    public VendingServiceLayerImplTest() {

    }

    @Test
    public void testLoadMachine() {
        VendingDao stubDao = new VendingDaoStubImpl();
        testService = new VendingServiceLayerImpl(stubDao);
    }

    @Test
    public void testGetAllItemsInMachineEMPTYSTUB() {
        //ARRANGE
        VendingDao stubDao = new EmptyVendingDaoStub();
        testService = new VendingServiceLayerImpl(stubDao);

        //ACT
        List<Item> allItems = testService.getAllItemsInMachine();

        //ASSERT
        Assert.assertNotNull("Empty Items should not be null", allItems);
        Assert.assertEquals("Empty Items should have size 0", allItems.size(), 0);
    }

    @Test
    public void testGetAllItemsWITHONEITEM() {
        //ARRANGE
        Item testItem = new Item("1", "Candy Bar", new BigDecimal(1.5), 4);
        SingleItemDaoStub stubDao = new SingleItemDaoStub(testItem);
        testService = new VendingServiceLayerImpl(stubDao);

        List<Item> allItems = null;

        //ACT
        allItems = testService.getAllItemsInMachine();

        //ASSERT
        Assert.assertNotNull("Items should not be null", allItems);
        Assert.assertEquals("Items should have size of 1", allItems.size(), 1);
        Assert.assertTrue("Test item should be there", allItems.contains(testItem));

    }

    @Test
    public void testGetOneItem() {
        //ARRANGE
        Item testItem = new Item("1", "Candy Bar", new BigDecimal(1.5), 4);
        SingleItemDaoStub stubDao = new SingleItemDaoStub(testItem);
        testService = new VendingServiceLayerImpl(stubDao);

        //ACT
        testService.getOneItem(testItem.getSlot());

        //ASSERT
        Assert.assertEquals("Should have same name", testItem.getName(), "Candy Bar");
        Assert.assertEquals("Should have quantity of 4", testItem.getQuantity(), 4);

    }

    @Test
    public void testPurchaseItem() throws Exception {

        Item testItem = new Item("1", "Candy Bar", new BigDecimal(1.0), 4);
        SingleItemDaoStub stubDao = new SingleItemDaoStub(testItem);
        testService = new VendingServiceLayerImpl(stubDao);
        BigDecimal money = new BigDecimal(1.43).setScale(2, RoundingMode.HALF_UP);
        BigDecimal cost = testItem.getCost();

        BigDecimal change = money.subtract(cost);

        ChangePurse changePurse = new ChangePurse(change);

        testService.purchaseItem(testItem.getSlot(), money);

        Assert.assertEquals("Item quantity should be one less", 3,
                testItem.getQuantity());
        Assert.assertEquals("Number of quarters should be 1", 1,
                changePurse.getNumQuarters());
        Assert.assertEquals("Number of dimes should be 1", 1,
                changePurse.getNumDimes());
        Assert.assertEquals("Number of nickels should be 1", 1,
                changePurse.getNumNickels());
        Assert.assertEquals("Number of pennies should be 3", 3,
                changePurse.getNumPennies());

    }
    
    @Test
    public void testPurchaseItemExactAmount() throws Exception {

        Item testItem = new Item("1", "Candy Bar", new BigDecimal(1.00), 4);
        SingleItemDaoStub stubDao = new SingleItemDaoStub(testItem);
        testService = new VendingServiceLayerImpl(stubDao);
        BigDecimal money = new BigDecimal(1.00).setScale(2, RoundingMode.HALF_UP);
        BigDecimal cost = testItem.getCost();

        BigDecimal change = money.subtract(cost);

        ChangePurse changePurse = new ChangePurse(change);

        testService.purchaseItem(testItem.getSlot(), money);

        Assert.assertEquals("Item quantity should be one less", 3,
                testItem.getQuantity());
        Assert.assertEquals("Number of quarters should be 0", 0,
                changePurse.getNumQuarters());
        Assert.assertEquals("Number of dimes should be 0", 0,
                changePurse.getNumDimes());
        Assert.assertEquals("Number of nickels should be 0", 0,
                changePurse.getNumNickels());
        Assert.assertEquals("Number of pennies should be 0", 0,
                changePurse.getNumPennies());

    }

    @Test
    public void testInsufficientFunds() throws Exception {

        Item testItem = new Item("1", "Candy Bar", new BigDecimal(1.50), 4);
        SingleItemDaoStub stubDao = new SingleItemDaoStub(testItem);
        testService = new VendingServiceLayerImpl(stubDao);
        BigDecimal money = new BigDecimal(1.25).setScale(2, RoundingMode.HALF_UP);
        BigDecimal cost = testItem.getCost();

        BigDecimal change = money.subtract(cost);

        try {
            testService.purchaseItem(testItem.getSlot(), money);
            Assert.fail("Should throw an exception!");
        } catch (VendingInsufficientFundsException e) {
            String exceptionMsg = e.getMessage();
            Assert.assertEquals(exceptionMsg, "Sorry but that costs $"
                    + cost + ". You only inserted $" + money);
        }
    }
    
    @Test
    public void testNoSuchItem () throws Exception {
        Item testItem = new Item("1", "Candy Bar", new BigDecimal(1.50), 4);
        SingleItemDaoStub stubDao = new SingleItemDaoStub(testItem);
        testService = new VendingServiceLayerImpl(stubDao);
        BigDecimal money = new BigDecimal(1.25).setScale(2, RoundingMode.HALF_UP);
        
        try {
            testService.purchaseItem("2", money);
        } catch (VendingPersistenceException e){
            String exceptionMsg = e.getMessage();
            Assert.assertEquals(exceptionMsg, "No such item exists!");
        }
    }
    
    @Test
    public void testOutOfStock () throws Exception {
        Item testItem = new Item("1", "Candy Bar", new BigDecimal(1.50), 0);
        SingleItemDaoStub stubDao = new SingleItemDaoStub(testItem);
        testService = new VendingServiceLayerImpl(stubDao);
        BigDecimal money = new BigDecimal(1.25).setScale(2, RoundingMode.HALF_UP);
        
        try {
            testService.purchaseItem("1", money);
        } catch (VendingNoItemInventoryException e) {
            String exceptionMsg = e.getMessage();
            Assert.assertEquals(exceptionMsg, "Sorry but we are "
                    + "currently out of " + testItem.getName() + "s");
        }
    }

}
