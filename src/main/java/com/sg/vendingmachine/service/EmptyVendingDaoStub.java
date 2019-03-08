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
public class EmptyVendingDaoStub implements VendingDao {
    
    //stub DAO with no items 
    
    public EmptyVendingDaoStub() {

    }

    @Override
    public void loadAllItems() throws VendingPersistenceException {

    }

    @Override
    public void saveAllChanges() throws VendingPersistenceException {

    }

    @Override
    public Item addItem(Item anItem) {
        return null;
    }

    @Override
    public List<Item> getAllItems() {
        return new ArrayList<>();
    }

    @Override
    public Item getAnItem(String slotId) {
        return null;
    }

    @Override
    public void updateAnItem(String slotId, Item changedItem) {

    }

    @Override
    public Item removeAnItem(String slotId) {
        return null;
    }

}
