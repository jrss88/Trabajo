/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juanramon
 */
@Entity
@Table
@XmlRootElement
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "precio")
    private double precio;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "ratingAverage")
    private int ratingAverage;
    @ManyToOne
    private User uPublish;
    @Column
    @Pattern(regexp = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)", message = "Tipo de imágen inválido")
    private String imagen;
    
    

    public Product(){
    
        name="";
        precio=0.0;
        descripcion="";
        ratingAverage=0;
    
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
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.food.model.Product[ id=" + id + " ]";
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
     * @return the precio
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }



    /**
     * @return the imagen
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * @return the uPublish
     */
    public User getU() {
        return uPublish;
    }

    /**
     * @param u the uPublish to set
     */
    public void setU(User u) {
        this.uPublish = u;
    }

    /**
     * @return the ratingAverage
     */
    public int getRatingAverage() {
        return ratingAverage;
    }

    /**
     * @param ratingAverage the ratingAverage to set
     */
    public void setRatingAverage(int ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

 
    
}
