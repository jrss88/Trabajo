/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.frontend.controller;

import com.food.frontend.dao.DAOProduct;
import com.food.frontend.dao.DAOUser;
import com.food.model.Product;
import com.food.model.User;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import org.primefaces.event.RateEvent;
import org.primefaces.model.UploadedFile;
import utils.FileUpload;

/**
 *
 * @author juanramon
 */
@Named("productCtrl")
@ViewScoped
public class ProductController implements Serializable {

    @Inject
    private DAOProduct daoProduct;
    @Inject
    private DAOUser daoUser;
    private Product product;

    @PostConstruct
    public void init() {

        product = new Product();

    }

    public ProductController() {

    }

    public void createProduct(Part file) throws IOException {

        String img = file.getName();
        FileUpload fichero = new FileUpload();

        int valoracion = 0;
        String remoteUser = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        User u = daoUser.getUserByName(remoteUser);
        Long id_user = u.getId();
        this.product.setU(daoUser.getUser(id_user));

        this.product.setName(this.product.getName());
        this.product.setPrecio(this.product.getPrecio());
        this.product.setDescripcion(this.product.getDescripcion());
        this.product.setRatingAverage(valoracion);

        if (fichero.upload(file)) {

            this.product.setImagen(fichero.getNombre());

            try {
                getdaoProduct().createProduct(this.product);
                FacesContext.getCurrentInstance().addMessage("registroCorrecto", new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "El registro del producto se ha realizado correctamente."));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage("registroInCorrecto", new FacesMessage(FacesMessage.SEVERITY_INFO, "InCorrecto", "El registro del producto no se ha realizado correctamente."));

            }

        }
        if (!fichero.upload(file)) {
            this.product.setImagen("defecto.png");
            try {
                getdaoProduct().createProduct(this.product);
                FacesContext.getCurrentInstance().addMessage("registroCorrecto", new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "El registro del producto se ha realizado correctamente."));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage("registroInCorrecto", new FacesMessage(FacesMessage.SEVERITY_INFO, "InCorrecto", "El registro del producto no se ha realizado correctamente."));

            }

        }
        
    }

    public void editProduct(Product p, Long id) {

        daoProduct.editProduct(p, id);
    }

    public Product getProduct(Long id) {

        Product p = daoProduct.getProduct(id);
        return p;
    }

    /**
     * @return the daoProduct
     */
    public DAOProduct getdaoProduct() {
        return daoProduct;
    }

    /**
     * @param productREST the daoProduct to set
     */
    public void setdaoProduct(DAOProduct daoProduct) {
        this.daoProduct = daoProduct;
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

    public List<Product> getProductsUser(Long id_user) {

        List<Product> l = daoProduct.getProducts(id_user);
        return l;
    }

    public void deleteProduct(Long id) {

        daoProduct.remove(id);
    }

    public List<Product> ListenerProductosUser(AjaxBehaviorEvent e) {

        Long id_user = (Long) e.getComponent().getAttributes().get("id_user");
        List<Product> l = daoProduct.getProducts(id_user);
        return l;
    }

    public void onrate(RateEvent rateEvent) {

        
       
        int rating = ((Integer) rateEvent.getRating()).intValue();
        this.product.setRatingAverage(rating);
        editProduct(this.product,this.product.getId());

    }
    
    public void selectedProduct(ActionEvent ae) {

        product = (Product) ae.getComponent().getAttributes().get("prod");
        

    }
}
