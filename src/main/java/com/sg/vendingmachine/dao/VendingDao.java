/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import java.util.List;

/**
 *
 * @author MadelineHebel
 */
public interface VendingDao {

    public void loadAllItems()
            throws VendingPersistenceException;

    public void saveAllChanges()
            throws VendingPersistenceException;

    public Item addItem(Item anItem);

    public List<Item> getAllItems();

    public Item getAnItem(String slotId);

    public void updateAnItem(String slotId, Item changedItem);

    public Item removeAnItem(String slotId);

}
