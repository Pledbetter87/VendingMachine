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
import com.sg.vendingmachine.service.exceptions.VendingNoSuchItemException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MadelineHebel
 */
public class VendingServiceLayerImpl implements VendingService {

    private VendingDao itemDao;

    public VendingServiceLayerImpl(VendingDao itemDao) {
        this.itemDao = itemDao;

    }

    List<Item> itemsFromDao = new ArrayList<>();

    @Override
    public void loadMachine() throws VendingPersistenceException {
        itemDao.loadAllItems();

    }

    @Override
    public List<Item> getAllItemsInMachine() {
        itemsFromDao = itemDao.getAllItems();
        return itemsFromDao;

    }

    @Override
    public Item getOneItem(String slotId) {
        Item anItem = itemDao.getAnItem(slotId);
        return anItem;
    }

    @Override
    public ChangePurse purchaseItem(String slotId, BigDecimal money) throws
            VendingInsufficientFundsException, VendingNoItemInventoryException,
            VendingPersistenceException {
        
        //gets the Item object from the DAO that corresponds with the user
        // slotId entered
        Item toBuy = itemDao.getAnItem(slotId);
        
        //check to see that the item exists
        if (toBuy == null) {
            throw new VendingPersistenceException("No such item exists!");
        }
        
        //get the qauntity 
        int numOf = toBuy.getQuantity();
        //get Item cost
        BigDecimal cost = toBuy.getCost();
        //calculate TOTAL change due back
        BigDecimal change = money.subtract(cost).setScale(2);
        
        //ensure Item is in stock
        if (numOf <= 0) {
            String out = toBuy.getName();
            throw new VendingNoItemInventoryException("Sorry but we are "
                    + "currently out of " + out + "s");
        }
        
        //ensure customer has enough money
        int diff = money.compareTo(cost);
        if (diff <= -1) {
            throw new VendingInsufficientFundsException("Sorry but that costs $"
                    + cost + ". You only inserted $" + money);
        }
        
        //instaniate a new ChangePurse - passing in the total change due value
        ChangePurse changePurse = new ChangePurse(change);
        
        //subtract 1 from the item quantity 
        int quantity = toBuy.getQuantity();
        quantity = quantity - 1;
        toBuy.setQuantity(quantity);
        
        //tell the DAO to save changes 
        itemDao.saveAllChanges();
        
        //return the changePurse to the controller
        return changePurse;
    }

}
