/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dto;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import java.math.BigDecimal;

/**
 *
 * @author MadelineHebel
 */
public class ChangePurse {

    private int numPennies;
    private int numNickels;
    private int numDimes;
    private int numQuarters;

    public ChangePurse(int numPennies, int numNickels, int numDimes, int numQuarters) {
        this.numPennies = numPennies;
        this.numNickels = numNickels;
        this.numDimes = numDimes;
        this.numQuarters = numQuarters;
    }

    public ChangePurse() {
    }
    
    //method to calculate change back
    public ChangePurse(BigDecimal changeDue) {
        //multiply by 100 to work with whole number
        changeDue = changeDue.multiply(new BigDecimal(100));
        //convert BigDecimal to int
        numPennies = changeDue.intValue();
        
        // divide by 25 to get out number of quarters
        numQuarters = numPennies / 25;
        // modulas 25 to get remainder after quarters and assign it to pennies
        numPennies = numPennies % 25;

        numDimes = numPennies / 10;
        numPennies = numPennies % 10;

        numNickels = numPennies / 5;
        numPennies = numPennies % 5;

    }

    public int getNumPennies() {
        return numPennies;
    }

    public void setNumPennies(int numPennies) {
        this.numPennies = numPennies;
    }

    public int getNumNickels() {
        return numNickels;
    }

    public void setNumNickels(int numNickels) {
        this.numNickels = numNickels;
    }

    public int getNumDimes() {
        return numDimes;
    }

    public void setNumDimes(int numDimes) {
        this.numDimes = numDimes;
    }

    public int getNumQuarters() {
        return numQuarters;
    }

    public void setNumQuarters(int numQuarters) {
        this.numQuarters = numQuarters;
    }

}
