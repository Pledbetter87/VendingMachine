/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingPersistenceException;
import com.sg.vendingmachine.dto.ChangePurse;
import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.service.exceptions.VendingInsufficientFundsException;
import com.sg.vendingmachine.service.exceptions.VendingNoItemInventoryException;
import com.sg.vendingmachine.service.exceptions.VendingNoSuchItemException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author MadelineHebel
 */
public interface VendingService {

    public void loadMachine()
            throws VendingPersistenceException;

    public List<Item> getAllItemsInMachine();

    public Item getOneItem(String slotId);

    public ChangePurse purchaseItem(String slotId, BigDecimal money)
            throws VendingInsufficientFundsException,
            VendingNoItemInventoryException,
            VendingPersistenceException;
}
