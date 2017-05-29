/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.frontend.controller;

import com.food.frontend.dao.DAOUser;
import com.food.model.User;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
@Named(value="mapCtrl")
@ViewScoped
public class GMap implements Serializable{
    
    @Inject
    private DAOUser daoUser;
    
    private final MapModel advancedModel = new DefaultMapModel();
    private String centerGeoMap = "37.1773363,-3.5985570999999936";
    private Boolean showtable;
    private float ratio;
    private Marker marker;
    
    
    @PostConstruct
    public void init() {

        marker= new Marker(null);
        List<User> list = this.getDaoUser().getUsers();
      
        for (User u : list) {

            //Introduzco los puntos de los usuarios registrados por latitud y longitud.
            if (u.getIsParticular() == true) {
                LatLng coord = new LatLng(u.getLatitud(), u.getLongitud());
                getAdvancedModel().addOverlay(new Marker(coord, u.getName(), "resources/images/defecto.png", "http://maps.google.com/mapfiles/ms/micons/blue-dot.png", u.getName() + "-........- " + u.getAddress()+ " -........- " + u.getPhone()));
                
                
            } else if (u.getIsProffesional() == true) {

                LatLng coord = new LatLng(u.getLatitud(), u.getLongitud());
                getAdvancedModel().addOverlay(new Marker(coord, u.getName(), "resources/images/defecto.png", "http://maps.google.com/mapfiles/ms/micons/yellow-dot.png", u.getName() + "-........- " + u.getAddress()+ " -........- " + u.getPhone()));
                    
            }

        }
    }
    
 
    
    public GMap(){}

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

    /**
     * @return the advancedModel
     */
    public MapModel getAdvancedModel() {
        return advancedModel;
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
    
    public void onMarkerSelect(OverlaySelectEvent event) {

        marker = (Marker) event.getOverlay();
       
        //User asociadoSeleccionado=(User) marker.getData();
        
        
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
    
}
