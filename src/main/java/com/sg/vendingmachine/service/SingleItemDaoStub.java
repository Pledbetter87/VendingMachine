/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingDao;
import com.sg.vendingmachine.dao.VendingPersistenceException;
import com.sg.vendingmachine.dto.Item;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MadelineHebel
 */
public class SingleItemDaoStub implements VendingDao {

    //stub DAO with one item
        
    public final Item onlyItem;

    public SingleItemDaoStub(Item testItem) {
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
        if (anItem.getSlot().equalsIgnoreCase(onlyItem.getSlot())) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> oneItem = new ArrayList<>();
        oneItem.add(onlyItem);
        return oneItem;
    }

    @Override
    public Item getAnItem(String slotId) {
        if (slotId.equalsIgnoreCase(onlyItem.getSlot())) {
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
        if (slotId.equalsIgnoreCase(onlyItem.getSlot())) {
            return onlyItem;
        } else {
            return null;
        }
    }

}
