/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.frontend.controller;

import com.food.frontend.dao.DAORol;
import com.food.frontend.dao.DAOUser;
import com.food.model.Rol;
import com.food.model.User;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author juanramon
 */
@Named("userCtrl")
@ViewScoped
public class UserController implements Serializable {

    private static Logger log = Logger.getLogger(User.class.getName());

    @Inject
    private DAOUser daoUser;
    private User user;

    @Inject
    private DAORol daoRol;
    
    private Rol rol;
    
    private Boolean showtable;

    @PostConstruct
    public void init() {

        //String remoteUser = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        
            user= new User();
            rol= new Rol();
        //user = daoUser.getUserByName(remoteUser);

    }

    public UserController() {

    }

    /**
     * @return the daoUser
     */
    public DAOUser getDaoUser() {
        return daoUser;
    }

    /**
     * @param daoUserRest the daoUser to set
     */
    public void setDaoUser(DAOUser daoUserRest) {
        this.daoUser = daoUserRest;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    public String logout() throws ServletException {
        
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        request.logout() ;
        facesContext.getExternalContext().invalidateSession();
        return "faces/index.xhtml?faces-redirect=true";
        
    }
    
    public Long getIdUser(String name) {

        User u = this.getDaoUser().getUserByName(name);
        return u.getId();

    }

    public void addNewUser() {

        double latitud = 0;
        double longitud = 0;
        String address = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("route");
        String number = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("street_number");
        String locality = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("locality");
        address = address + ", " + number + ", " + locality;
        String latitudS = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("hidLat");
        if (latitudS != null) {
            latitud = Double.parseDouble(latitudS);
        }
        String longitudS = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("hidLong");
        if (longitudS != null) {
            longitud = Double.parseDouble(longitudS);
        }
        this.user.setAddress(address);
        this.user.setLatitud(latitud);
        this.user.setLongitud(longitud);

        rol.setName(this.user.getName());
        rol.setRol("user");
        daoRol.create_Rol(rol);
        try {

            daoUser.createUser(getUser());
            FacesContext.getCurrentInstance().addMessage("registroCorrecto", new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Se ha registrado correctamente correctamente."));

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage("registroIncorrecto", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario no añadido correctamente", ex.getMessage()));

        }

    }

    public void editPerfil(User u, Long id) {

        try {

            getDaoUser().editUser(u, u.getId());
            FacesContext.getCurrentInstance().addMessage("edicionCorrecta", new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario editado correctamente", this.user.toString()));

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage("edicionIncorrecta", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario no editado correctamente", ex.getMessage()));

        }

    }

    public List<User> getUsers() {

        List<User> users = this.getDaoUser().getUsers();
        return users;
    }

    public User getUser(Long id) {

        User u = this.getDaoUser().getUser(id);
        return u;
    }

    public User getUserByName(String name) {

        User u = daoUser.getUserByName(name);
        return u;

    }
    /**
     * @return the showtable
     */
    public Boolean getShowtable() {
        return showtable;
    }

    /**
     * @param showtable the showtable to set
     */
    public void setShowtable(Boolean showtable) {
        this.showtable = showtable;
    }

    public String enabletable() {
        showtable = true;
        return "";
    }

    /**
     * @return the daoRol
     */
    public DAORol getDaoRol() {
        return daoRol;
    }

    /**
     * @param daoRol the daoRol to set
     */
    public void setDaoRol(DAORol daoRol) {
        this.daoRol = daoRol;
    }

    /**
     * @return the rol
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }

}
