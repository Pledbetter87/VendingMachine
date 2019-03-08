/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author MadelineHebel
 */
public class VendingDaoFileImpl implements VendingDao {

    private final String ITEM_FILE;
    public static final String DELIMITER = "::";

    private Map<String, Item> inventory = new HashMap<>();
    
    public VendingDaoFileImpl() {
        ITEM_FILE = "item.txt";
    }

    public VendingDaoFileImpl(String itemTextFile) {
        ITEM_FILE = itemTextFile;
    }

    @Override
    public void loadAllItems() throws VendingPersistenceException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file using a Buffered Reader and
            // a File Reader
            scanner = new Scanner(
                    new BufferedReader(new FileReader(ITEM_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingPersistenceException(
                    "-_- Could not load Item data into memory.", e);
        }
        
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentDVD holds the most recent item unmarshalled
        Item currentItem;
        // decalred above and will be used in the while loop below - uses less
        //memory because they can be re used inside the loop rather than the loop
        //creating a new instance every time it iterates
        
        // Go through ITEM_FILE line by line, decoding each line into an 
        // Item object by calling the unmarshallItem method.
        
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // unmarshall the line into a, Item
            currentItem = unmarshallItem(currentLine);

            // We are going to use the slotId as the map key for our Item object.
            // Put currentItem into the map using slotId as the key
            inventory.put(currentItem.getSlot(), currentItem);
        }
        // close scanner
        scanner.close();
    }

    @Override
    public void saveAllChanges() throws VendingPersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(ITEM_FILE));//file is blank at this point
        } catch (IOException e) { //happens when the file doesnt exist or you
            // do not have permission to write to that file
            throw new VendingPersistenceException( //throws DAO excep. because we have
                    //translated both into DAO. Throws exception into the controller
                    //which displays error to the user and shuts down. Allows us
                    //to use the same functionality for both.
                    "Could not save Item data.", e);
        }

        // Write out the Item objects to the inventory file.
        //create a string titles itemsAsText
        String itemAsText;
        //get a list to iterate over
        List<Item> itemList = this.getAllItems();
        for (Item currentItem : itemList) {
            // turn an Item into a String
            itemAsText = marshallItem(currentItem);
            // write the Item object to the file
            out.println(itemAsText);
            // force PrintWriter to write line to the file
            out.flush();
        }
        // Clean up
        out.close();
    }

    @Override
    public Item addItem(Item anItem) {
        return inventory.put(anItem.getSlot(), anItem);
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> itemsAsList;
        itemsAsList = new ArrayList<>(inventory.values());
        return itemsAsList;
    }

    @Override
    public Item getAnItem(String slotId) {
        return inventory.get(slotId);
    }

    @Override
    public void updateAnItem(String slotId, Item changedItem) {
        inventory.replace(changedItem.getSlot(), changedItem);
    }

    @Override
    public Item removeAnItem(String slotId) {
        Item removedItem = inventory.remove(slotId);
        return removedItem;
    }

    private Item unmarshallItem(String itemAsText) {

        String[] itemTokens = itemAsText.split(DELIMITER); //declaring the string
        // array that objects will be stored as - calling it itemTokens - the
        // .split(DELIMITER) is the action to chop up the line of text

        Item itemFromFile = new Item();

        itemFromFile.setSlot(itemTokens[0]);

        itemFromFile.setName(itemTokens[1]);

        //must parse the DOUBLE cost to a STRING
        BigDecimal itemCost = new BigDecimal(itemTokens[2]);
        itemFromFile.setCost(itemCost);

        //must parse the INT quantity to a STRING
        int quantity = Integer.parseInt(itemTokens[3]);
        itemFromFile.setQuantity(quantity);

        return itemFromFile;
    }

    private String marshallItem(Item anItem) {
        // We need to turn an Item object into a line of text for our file.
        // For example, we need an in memory object to end up like this:
        // 4321::Charles::Babbage::Java-September1842

        // It's not a complicated process. Just get out each property,
        // and concatenate with our DELIMITER as a kind of spacer. 
        // Start with the title, since that's supposed to be first.
        String itemAsText = anItem.getSlot() + DELIMITER;

        // add the rest of the properties in the correct order:
        itemAsText += anItem.getName() + DELIMITER;

        itemAsText += anItem.getCost() + DELIMITER;

        itemAsText += anItem.getQuantity();

        // have now turned an Item to text!
        return itemAsText;
    }

}
