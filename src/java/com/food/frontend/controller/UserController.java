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
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
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

    @Inject
    private DAOUser daoUserRest;
    private User user = new User();
    
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    
    private final MapModel advancedModel = new DefaultMapModel();
    private String centerGeoMap = "41.850033, -87.6500523";
    private Boolean showtable;
    private float ratio;
    private Marker marker;

    @PostConstruct
    public void init() {

        List<User> list = this.getDaoUserRest().getUsers();
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
     * @return the daoUserRest
     */
    public DAOUser getDaoUserRest() {
        return daoUserRest;
    }

    /**
     * @param daoUserRest the daoUserRest to set
     */
    public void setDaoUserRest(DAOUser daoUserRest) {
        this.daoUserRest = daoUserRest;
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

            getDaoUserRest().create_JSON(getUser());
            getFacesContext().addMessage("registroCorrecto", new FacesMessage(FacesMessage.SEVERITY_INFO, "Nuevo usuario añadido correctamente", getUser().toString()));

        } catch (Exception ex) {
            getFacesContext().addMessage("registroIncorrecto", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario no añadido correctamente", ex.getMessage()));

        }

    }
    public void editPerfil() {

     
    
        try {

            getDaoUserRest().edit_JSON(this.getUser(),this.getUser().getId());
            getFacesContext().addMessage("edicionCorrecta", new FacesMessage(FacesMessage.SEVERITY_INFO, "Nuevo usuario editado correctamente", getUser().toString()));

        } catch (Exception ex) {
            getFacesContext().addMessage("edicionIncorrecta", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario no editado correctamente", ex.getMessage()));

        }

    }
    public List<User> getUsers() {

        List<User> users = this.getDaoUserRest().getUsers();
        
        return users;
    }
    public User getUser(Long id) {

        User u = this.getDaoUserRest().getUser(id);
        
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
