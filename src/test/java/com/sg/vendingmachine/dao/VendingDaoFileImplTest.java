/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author MadelineHebel
 */
public class VendingDaoFileImplTest {

    VendingDao testDao;

    Item[] testItems = {
        new Item("A1", "Snickers", new BigDecimal(1.00), 5),
        new Item("A2", "Miky Way", new BigDecimal(1.00), 3),
        new Item("B3", "Reese's", new BigDecimal(.75), 2),
        new Item("B5", "BBQ Chips", new BigDecimal(.50), 6),
        new Item("C2", "Pretzels", new BigDecimal(.50), 3),
        new Item("D4", "100 Grand", new BigDecimal(1.00), 1)
    };

    private List<Item> inventory = Arrays.asList(testItems);

    @Before
    public void setUp() throws Exception {
        String testFile = "testItem.txt";
        new FileWriter(testFile);
        new FileReader(testFile);
        testDao = new VendingDaoFileImpl(testFile);

    }

    @Test
    public void testAddGetItem() throws Exception {
        //ARRANGE
        Item item = inventory.get(0);

        //ACT
        Item shouldBeNull = testDao.addItem(item);//no previous value

        Item shouldBeItem = testDao.getAnItem(item.getSlot());//has previous value

        //ASSERT
        Assert.assertNull("Should not have candy", shouldBeNull);
        Assert.assertNotNull("Oh piece of candy", shouldBeItem);

        //COMPARE
        Assert.assertEquals(shouldBeItem.getName(), item.getName());
        Assert.assertEquals(shouldBeItem.getCost(), item.getCost());
        Assert.assertEquals(shouldBeItem.getQuantity(), item.getQuantity());
        Assert.assertEquals(shouldBeItem.getSlot(), item.getSlot());

    }

    @Test
    public void testGetAllItems() {
        //add all to dao
        testDao.addItem(testItems[0]);
        testDao.addItem(testItems[1]);
        testDao.addItem(testItems[2]);
        testDao.addItem(testItems[3]);
        testDao.addItem(testItems[4]);
        testDao.addItem(testItems[5]);

        //retreive as list
        List<Item> allItems = testDao.getAllItems();

        //check general contents
        Assert.assertNotNull(allItems);
        Assert.assertEquals(6, allItems.size());

        //check specifics
        Assert.assertTrue(testDao.getAllItems().contains(testItems[0]));
        Assert.assertTrue(testDao.getAllItems().contains(testItems[1]));
        Assert.assertTrue(testDao.getAllItems().contains(testItems[2]));
        Assert.assertTrue(testDao.getAllItems().contains(testItems[3]));
        Assert.assertTrue(testDao.getAllItems().contains(testItems[4]));
        Assert.assertTrue(testDao.getAllItems().contains(testItems[5]));

    }

    @Test
    public void testRemoveItem() {
        //add to dao
        testDao.addItem(testItems[2]);
        testDao.addItem(testItems[5]);

        //remove one
        Item removedItem = testDao.removeAnItem("B3");

        //check removal
        Assert.assertEquals(removedItem, testItems[2]);

        List<Item> allItems = testDao.getAllItems();
        Assert.assertEquals(1, allItems.size());
        Assert.assertFalse(allItems.contains(testItems[2]));
        Assert.assertTrue(allItems.contains(testItems[5]));

        //remove second item
        removedItem = testDao.removeAnItem("D4");

        //check
        Assert.assertEquals(removedItem, testItems[5]);

        //get list and make sure its empty
        allItems = testDao.getAllItems();
        Assert.assertTrue(allItems.isEmpty());

    }

    @Test
    public void testUpdateItem() {
        //add item
        testDao.addItem(testItems[3]);

        //create new
        Item zero = new Item("B5", "Zero Bar", new BigDecimal(1.00), 3);

        //update slot 1 to contain zero
        testDao.updateAnItem("B5", zero);

        //create new item with the vaule of item in slot 1
        Item newItem = testDao.getAnItem("B5");

        //checking to see if slot 1 is dracula
        Assert.assertEquals(zero, newItem);

    }

    @Test
    public void testPersistence() throws VendingPersistenceException {

        Item item1 = inventory.get(0);
        String slot1 = item1.getSlot();
        Item item2 = inventory.get(1);
        String slot2 = item2.getSlot();

        testDao.addItem(item1);
        testDao.addItem(item2);

        testDao.saveAllChanges();

        testDao.loadAllItems();

        Item new1 = testDao.getAnItem(slot1);
        Item new2 = testDao.getAnItem(slot2);

        Assert.assertEquals(new1, item1);
        Assert.assertEquals(new2, item2);
    }

}
