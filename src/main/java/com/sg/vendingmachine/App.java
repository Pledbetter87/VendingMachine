/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine;

import com.sg.vendingmachine.controller.VendingController;
import com.sg.vendingmachine.dao.VendingPersistenceException;
import com.sg.vendingmachine.service.exceptions.VendingInsufficientFundsException;
import com.sg.vendingmachine.service.exceptions.VendingNoItemInventoryException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author MadelineHebel
 */
public class App {

    public static void main(String[] args) throws VendingNoItemInventoryException,
            VendingInsufficientFundsException, VendingPersistenceException {

//        UserIO myIo = new UserIOConsoleImpl();
//        VendingMachineView myView = new VendingMachineView(myIo);
//        VendingDao myDao = new VendingDaoFileImpl();
//        VendingService myService = new VendingServiceLayerImpl(myDao);
//        VendingController myController
//                = new VendingController(myView, myService);
//        
//        myController.run();

        //Spring DI for MAXIMUM LOOSE COUPLING - classes and interfaces are 
        //easier to alter/change because they are not hard coded - not reliant
        //on a single instance - externalizes to context file
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        VendingController controller = ctx.getBean("controller", VendingController.class);
        controller.run();

    }

}
