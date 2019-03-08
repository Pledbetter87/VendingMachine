/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingDao;
import com.sg.vendingmachine.dao.VendingPersistenceException;
import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MadelineHebel
 */
public class VendingDaoStubImpl implements VendingDao {

    //stub DAO used for testing only
    //contains canned method data - had no ability to persist data
    
    public Item onlyItem;

    public VendingDaoStubImpl() {
        onlyItem = new Item();
        onlyItem.setSlot("1");
        onlyItem.setName("Candy Bar");
        BigDecimal cost = new BigDecimal(1.5);
        onlyItem.setCost(cost);
        onlyItem.setQuantity(5);
    }

    public VendingDaoStubImpl(Item testItem) {
        this.onlyItem = testItem;
    }

    @Override
    public void loadAllItems() throws VendingPersistenceException {

    }

    @Override
    public void saveAllChanges() throws VendingPersistenceException {

    }

    @Override
    public Item addItem(Item anItem) {
        if (anItem.equals(onlyItem)) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(onlyItem);
        return itemList;
    }

    @Override
    public Item getAnItem(String slotId) {
        if (slotId.equals(onlyItem.getSlot())) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public void updateAnItem(String slotId, Item changedItem) {

    }

    @Override
    public Item removeAnItem(String slotId) {
        if (slotId.equals(onlyItem.getSlot())) {
            return onlyItem;
        } else {
            return null;
        }
    }

}
