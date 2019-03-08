/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service.exceptions;

/**
 *
 * @author MadelineHebel
 */
public class VendingNoSuchItemException extends Exception {

    public VendingNoSuchItemException(String message) {
        super(message);
    }

    public VendingNoSuchItemException(String message, Throwable cause) {
        super(message, cause);
    }

}
