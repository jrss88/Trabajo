/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.frontend.controller;

import com.food.frontend.dao.DAOPrivateMessage;
import com.food.frontend.dao.DAOUser;
import com.food.model.PrivateMessage;
import com.food.model.User;
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
    
    @Inject
    private DAOUser daoUser;
   
    private PrivateMessage pmsg;
    private String destination;

    @PostConstruct
    public void init() {

       
        pmsg= new PrivateMessage();

    }

    public PrivateMessageController() {}
    
    public void dataPM(ActionEvent event){
        this.destination =(String) event.getComponent().getAttributes().get("nameUser");
    }
    public void sendPrivateMessage(ActionEvent event){
    
        
        String emisor = (String) event.getComponent().getAttributes().get("emisor");
        User uEmisor = daoUser.getUserByName(emisor);
        Long id_emisor = uEmisor.getId();
        pmsg.setuEmisor(daoUser.getUser(id_emisor));
        
        String dest = (String) event.getComponent().getAttributes().get("destinatario");
        User uDest = daoUser.getUserByName(dest);
        Long id_dest = uDest.getId();
        pmsg.setuReceptor(daoUser.getUser(id_dest));
        
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

    /**
     * @return the daoUser
     */
    public DAOUser getDaoUser() {
        return daoUser;
    }

    /**
     * @param daoUser the daoUser to set
     */
    public void setDaoUser(DAOUser daoUser) {
        this.daoUser = daoUser;
    }

}
