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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    private String destination;

    @PostConstruct
    public void init() {

       
        pmsg= new PrivateMessage();

    }

    public PrivateMessageController() {

        
        
    }
    
    public void dataPM(ActionEvent event){
        this.destination =(String) event.getComponent().getAttributes().get("nameUser");
    }
    public void sendPrivateMessage(ActionEvent event){
    
        String dest = (String) event.getComponent().getAttributes().get("destinatario");
        String emisor = (String) event.getComponent().getAttributes().get("emisor");

        try {

            daoPrivateMessage.create(getPmsg());
            pmsg.setMessage("");
            FacesContext.getCurrentInstance().addMessage("envioCorrecto", new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Se ha registrado correctamente correctamente."));

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage("envioIncorrecto", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario no añadido correctamente", ex.getMessage()));

        }
        
    
    }

    /**
     * @return the destiny
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @param destiny the destiny to set
     */
    public void setDestination(String destiny) {
        this.destination = destiny;
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
