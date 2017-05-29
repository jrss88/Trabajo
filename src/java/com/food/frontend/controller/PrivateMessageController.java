/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.frontend.controller;

import com.food.frontend.dao.DAOPrivateMessage;
import com.food.model.PrivateMessage;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author JR
 */
@Named(value="privateMessageCtrl")
@ViewScoped
public class PrivateMessageController implements Serializable{
    
    
    @Inject
    private DAOPrivateMessage daoPrivateMessage;
   
    private PrivateMessage pmsg;
    private String destiny;

    @PostConstruct
    public void init() {

       
        pmsg= new PrivateMessage();

    }

    public PrivateMessageController() {

        
        
    }
    
    public void dataPM(ActionEvent event){
    
        
        this.destiny =(String) event.getComponent().getAttributes().get("nameUser");
        System.out.print(destiny);
    
    }
    public void sendPrivateMessage(ActionEvent event){
    
        
        
    
    }

    /**
     * @return the destiny
     */
    public String getDestiny() {
        return destiny;
    }

    /**
     * @param destiny the destiny to set
     */
    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    /**
     * @return the pmsg
     */
    public PrivateMessage getPmsg() {
        return pmsg;
    }

    /**
     * @param pmsg the pmsg to set
     */
    public void setPmsg(PrivateMessage pmsg) {
        this.pmsg = pmsg;
    }

}
