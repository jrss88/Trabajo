/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juanramon
 */
@Entity
@Table
@XmlRootElement
public class PrivateMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private User uEmisor;
    @OneToOne
    private User uReceptor;
    private String message;
    private Date date;

    public PrivateMessage() {

        
        message = "";
        date = new Date();

    }

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
        if (!(object instanceof PrivateMessage)) {
            return false;
        }
        PrivateMessage other = (PrivateMessage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.food.model.PrivateMessage[ id=" + id + " ]";
    }

    /**
     * @return the uEmisor
     */
    public User getuEmisor() {
        return uEmisor;
    }

    /**
     * @param uEmisor the uEmisor to set
     */
    public void setuEmisor(User uEmisor) {
        this.uEmisor = uEmisor;
    }

    /**
     * @return the uReceptor
     */
    public User getuReceptor() {
        return uReceptor;
    }

    /**
     * @param uReceptor the uReceptor to set
     */
    public void setuReceptor(User uReceptor) {
        this.uReceptor = uReceptor;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the fecha
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

}
