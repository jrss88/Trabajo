/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.frontend.controller;

import com.food.frontend.dao.DAOProduct;
import com.food.frontend.dao.DAOUser;
import com.food.model.Product;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.servlet.http.Part;
import org.primefaces.model.UploadedFile;


/**
 *
 * @author juanramon
 */
@ManagedBean(name = "productCtrl")
@ViewScoped
@RequestScoped
public class ProductController implements Serializable {

    @Inject
    private DAOProduct daoProductREST;
    @Inject
    private DAOUser daoUserREST;
    private Product product=new Product();;

    @PostConstruct
    public void init() {
      
    }

    public ProductController() {

    }

    public void createProduct(UploadedFile file)  {

        int valoracion = 3;
        
       
        String id_userr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id_user");
        Long id_user = new Long(Long.parseLong(id_userr));
        
        this.product.setU(daoUserREST.getUser(id_user));
        this.product.setImagen("defecto.png");
        this.product.setName(this.product.getName());
        this.product.setPrecio(this.product.getPrecio());
        this.product.setDescripcion(this.product.getDescripcion());
        this.product.setValoracion(valoracion);
        
        System.out.println(this.product.getDescripcion());
        System.out.println(this.product.getImagen());
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        try{
        getProductREST().create_JSON_Product(this.product);
        FacesContext.getCurrentInstance().addMessage("registroCorrecto", new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "El registro del producto se ha realizado correctamente."));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage("registroInCorrecto", new FacesMessage(FacesMessage.SEVERITY_INFO, "InCorrecto", "El registro del producto no se ha realizado correctamente."));

        }
    }

    
    public List<Product> getProducts() {

        List<Product> list = this.daoProductREST.getProducts();
        return list;
    }
    /**
     * @return the daoProductREST
     */
    public DAOProduct getProductREST() {
        return daoProductREST;
    }

    /**
     * @param productREST the daoProductREST to set
     */
    public void setProductREST(DAOProduct productREST) {
        this.daoProductREST = productREST;
    }

    /**
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

//    public List<Product> ListenerProductosUser(AjaxBehaviorEvent e) {
//
//        Long id_user = (Long) e.getComponent().getAttributes().get("id_user");
//        List<Product> l=daoProductREST.muestraProductosUser(id_user);
//        return l;
//    }
}
