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
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
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

        int valoracion = 3;
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

    public void editProduct(Part file, Product p) throws IOException {

        String img = file.getName();
        FileUpload fichero = new FileUpload();
        
        
        int valoracion = 3;
        
        

        System.out.print("asdasd asdasdasd");
        System.out.print(p.getId());
        
        String remoteUser = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        User u = daoUser.getUserByName(remoteUser);
        Long id_user = u.getId();
        product= daoProduct.getProduct(p.getId());
        product.setU(daoUser.getUser(id_user));
        
        this.product.setDescripcion(p.getDescripcion());
        product.setPrecio(p.getPrecio());

        
        if (fichero.upload(file)) {

            product.setImagen(fichero.getNombre());

            try {
                daoProduct.editProduct(this.product.getId(),this.product);
                FacesContext.getCurrentInstance().addMessage("registroCorrecto", new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "La edición del producto se ha realizado correctamente."));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage("registroInCorrecto", new FacesMessage(FacesMessage.SEVERITY_INFO, "InCorrecto", "La edición del producto no se ha realizado correctamente."));

            }

        }
        if (!fichero.upload(file)) {
            this.product.setImagen("defecto.png");
            try {
                daoProduct.editProduct(product.getId(),product) ;
                FacesContext.getCurrentInstance().addMessage("registroCorrecto", new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "La edición del producto se ha realizado correctamente."));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage("registroInCorrecto", new FacesMessage(FacesMessage.SEVERITY_INFO, "InCorrecto", "La edición del producto no se ha realizado correctamente."));

            }

        }

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
}
