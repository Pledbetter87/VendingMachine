/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.ui;

import com.sg.vendingmachine.dto.ChangePurse;
import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author MadelineHebel
 */
public class VendingMachineView {

    //dependency injection
    private UserIO io;

    //constructor that initializes the io member field
    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    public int printMenuGetSelection() {
        io.print("--------------------------------------------");
        io.print("1. Insert Money and Make Selection");
        io.print("2. Exit");
        io.print("--------------------------------------------");
        return io.readInt("Please enter menu selection", 1, 2);
    }

    public String getUserChoice() {
        int choice = io.readInt("Please make item selection: ");
        String userChoice = String.valueOf(choice);
        return userChoice;
    }

    public BigDecimal getFunds() {
        double funds = io.readDouble("Please enter funds");
        if (funds <= 0) {
            do {
                funds = io.readDouble("You must enter money to continue");
            } while (funds <= 0);
        }
        return BigDecimal.valueOf(funds).setScale(2, RoundingMode.HALF_UP);
    }

    public void showFunds(BigDecimal userFunds) {
        io.print("You have $" + userFunds + " to spend");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command");
    }

    public void displayExitBanner() {
        io.print("Happy hunting!");
    }

    public void displayWelcomeBanner() {
        io.print("");
        io.print("+*=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+*");
        io.print("||           WELCOME TO THE          ||");
        io.print("||               SILPH CO.           ||");
        io.print("||             POKE-VENDER           ||");
        io.print("*+=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=+*");
        io.print("     ------ THE VERY BEST! ------");
        io.print("");
    }

    public void errorMessage(String errorMessage) {
        io.print("ERROR");
        io.print(errorMessage);
    }

    public void displayChange(ChangePurse changeDue) {

        io.print("Please take your change: ");
        io.print(changeDue.getNumQuarters() + " quarters, " + changeDue.getNumDimes()
                + " dimes, " + changeDue.getNumNickels() + " nickels "
                + changeDue.getNumPennies() + " pennies.");
    }

    public void showItemBought(Item purchased) {
        String item = purchased.getName();
        io.print("Dispensing: your " + item);
    }

    public void displayItems(List<Item> inventory) {
        for (Item current : inventory) {
            int stock = current.getQuantity();
            if (stock >= 1) {
                io.print(current.getSlot() + "] " + current.getName()
                        + ": $" + current.getCost());
            } else {
                io.print(current.getSlot() + "] " + current.getName() + " - Currently Out of Stock");
            }
        }
    }

    public void moneyBack(ChangePurse changeDue) {

        io.print("Please take your change:");
        io.print(changeDue.getNumQuarters() + " quarters, " + changeDue.getNumDimes()
                + " dimes, " + changeDue.getNumNickels() + " nickels "
                + changeDue.getNumPennies() + " pennies.");
    }

}
