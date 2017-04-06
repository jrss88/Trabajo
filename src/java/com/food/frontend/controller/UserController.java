/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.frontend.controller;

import com.food.frontend.dao.DAOUser;
import com.food.model.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author juanramon
 */
@ManagedBean(name = "userCtrl")
@ViewScoped
@RequestScoped
public class UserController implements Serializable {

    private static Logger log = Logger.getLogger(User.class.getName());
    
    @Inject
    private DAOUser daoUser;
    private User user = new User();
    
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    
    private final MapModel advancedModel = new DefaultMapModel();
    private String centerGeoMap = "41.850033, -87.6500523";
    private Boolean showtable;
    private float ratio;
    private Marker marker;

    @PostConstruct
    public void init() {

        List<User> list = this.getDaoUser().getUsers();
        for (User u : list) {
            System.out.print(u);
            //Introduzco los puntos de los usuarios registrados por latitud y longitud.
            LatLng coord = new LatLng(u.getLatitud(), u.getLongitud());
            getAdvancedModel().addOverlay(new Marker(coord, "Nombre del ofertante"));

        }
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

  //utils for addMarkets to google Maps
    public MapModel getAdvancedModel() {
        return advancedModel;
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        setMarker((Marker) event.getOverlay());
        this.setShowtable(getShowtable());
        setShowtable((Boolean) true);
    }

    public void onGeocode(GeocodeEvent event) {
        List<GeocodeResult> results = event.getResults();

        if (results != null && !results.isEmpty()) {
            LatLng center = results.get(0).getLatLng();
            setCenterGeoMap(center.getLat() + "," + center.getLng());

            for (int i = 0; i < results.size(); i++) {
                GeocodeResult result = results.get(i);
                getAdvancedModel().addOverlay(new Marker(result.getLatLng(), result.getAddress()));
            }
        }
    }
    /**
     * @return the facesContext
     */
    public FacesContext getFacesContext() {
        return facesContext;
    }

    /**
     * @param facesContext the facesContext to set
     */
    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }
    public String logout() {
        String result = "/index?faces-redirect=true";

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            request.logout();
        } catch (ServletException e) {
            log.log(Level.SEVERE, "Failed to logout user!", e);
            result = "/errorlogin?faces-redirect=true";
        }

        return result;
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
        this.getUser().setAddress(address);
        this.getUser().setLatitud(latitud);
        this.getUser().setLongitud(longitud);
    
        try {

            getDaoUser().createUser(getUser());
            getFacesContext().addMessage("registroCorrecto", new FacesMessage(FacesMessage.SEVERITY_INFO, "Nuevo usuario añadido correctamente", getUser().toString()));

        } catch (Exception ex) {
            getFacesContext().addMessage("registroIncorrecto", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario no añadido correctamente", ex.getMessage()));

        }

    }
    public void editPerfil() {

     
    
        try {

            getDaoUser().editUser(this.getUser(),this.getUser().getId());
            getFacesContext().addMessage("edicionCorrecta", new FacesMessage(FacesMessage.SEVERITY_INFO, "Nuevo usuario editado correctamente", getUser().toString()));

        } catch (Exception ex) {
            getFacesContext().addMessage("edicionIncorrecta", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario no editado correctamente", ex.getMessage()));

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
    /**
     * @return the centerGeoMap
     */
    public String getCenterGeoMap() {
        return centerGeoMap;
    }

    /**
     * @param centerGeoMap the centerGeoMap to set
     */
    public void setCenterGeoMap(String centerGeoMap) {
        this.centerGeoMap = centerGeoMap;
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
     * @return the ratio
     */
    public float getRatio() {
        return ratio;
    }

    /**
     * @param ratio the ratio to set
     */
    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    /**
     * @return the marker
     */
    public Marker getMarker() {
        return marker;
    }

    /**
     * @param marker the marker to set
     */
    public void setMarker(Marker marker) {
        this.marker = marker;
    }
}
