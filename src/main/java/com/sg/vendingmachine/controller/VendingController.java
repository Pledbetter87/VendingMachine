/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.controller;

import com.sg.vendingmachine.ui.VendingMachineView;
import com.sg.vendingmachine.dao.VendingPersistenceException;
import com.sg.vendingmachine.dto.ChangePurse;
import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.service.VendingService;
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
public class VendingController {

    //dependency injection
    private VendingMachineView view;
    private VendingService service;

    //constructor method for property declarations 
    public VendingController(VendingMachineView view, VendingService service) {

        this.service = service;
        this.view = view;

    }

    //main run method
    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        BigDecimal funds = new BigDecimal(0);
        //load items from persistent storage
        try {
            service.loadMachine();//need exceptions here

            while (keepGoing) {
                try {
                    view.displayWelcomeBanner();
                    displayItems();
                    menuSelection = getMenuSelection();

                    switch (menuSelection) {
                        case 1:
                            //get user entered funds
                            funds = view.getFunds();
                            //have the view show user the funds they have entered
                            view.showFunds(funds);
                            //get item choice from user via the view
                            String item = view.getUserChoice();
                            //have the service layer get the slected item based on
                            // the slotId 
                            service.getOneItem(item);
                            //create a new ChangePurse and pass the selected item
                            // and user funds as its parameters to the purchase
                            // item method in the service layer 
                            ChangePurse change = service.purchaseItem(item, funds);
                            //have the view display the purchased item to the user
                            Item purchased = service.getOneItem(item);
                            view.showItemBought(purchased);
                            //have the view display the returned change to the user 
                            view.displayChange(change);
                            keepGoing = false;
                        case 2:
                            keepGoing = false;
                            break;
                        default:
                            unknownCommand();
                    }
                } catch (VendingNoItemInventoryException | VendingInsufficientFundsException e) {
                    view.errorMessage(e.getMessage());
                    //if exceptions are thrown - display error message - return 
                    // user funds as change 
                    ChangePurse moneyBack = new ChangePurse(funds);
                    view.moneyBack(moneyBack);
                    break;
                }

                exitMessage();
            }
        } catch (VendingPersistenceException e) {
            view.errorMessage(e.getMessage());
        }
    }

    public int getMenuSelection() {
        return view.printMenuGetSelection();
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

    public void displayItems() {
        List<Item> inventory = service.getAllItemsInMachine();
        view.displayItems(inventory);
    }

}
