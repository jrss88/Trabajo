/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.model;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juanramon
 */
@Entity
@Table
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(User.class.getName());
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    @Size(min=4,max=50,message="El nombre debe tener entre 4 y 25 caracteres") 
    private String name;
    @Column(name = "pass")
    @Size(min=4,max=50,message="El password debe tener entre 4 y 10 caracteres")
    private String pass;
    @Column(name = "address") 
    private String address;
    @Column(name = "latitud")
    private double latitud;
    @Column(name = "longitud")
    private double longitud;
    @Column(name = "email")
    @Pattern(regexp=".+@.+\\.[a-z]+",message="El formato del email no es correcto")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "isParticular")
    private boolean isParticular;
    @Column(name = "isProffesional")
    private boolean isProffesional;
    @Column(name = "payment")
    private int payment;
    @Column(name = "rating")
    private int rating;
    

    public User(){}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.food.model.User[ id=" + id + " ]";
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the latitud
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * @param latitud the latitud to set
     */
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    /**
     * @return the longitud
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

  

    /**
     * @return the payment
     */
    public int getPayment() {
        return payment;
    }

    /**
     * @param payment the payment to set
     */
    public void setPayment(int payment) {
        this.payment = payment;
    }


    /**
     * @return the isParticular
     */
    public boolean getIsParticular() {
        return isParticular;
    }

    /**
     * @param isParticular the isParticular to set
     */
    public void setIsParticular(boolean isParticular) {
        this.isParticular = isParticular;
    }

    /**
     * @return the isProffesional
     */
    public boolean getIsProffesional() {
        return isProffesional;
    }

    /**
     * @param isProffesional the isProffesional to set
     */
    public void setIsProffesional(boolean isProffesional) {
        this.isProffesional = isProffesional;
    }

    /**
     * @return the rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

}
