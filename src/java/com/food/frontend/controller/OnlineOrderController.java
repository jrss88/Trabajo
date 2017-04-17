/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.frontend.controller;

import com.food.frontend.dao.DAOOrder;
import com.food.frontend.dao.DAOUser;
import com.food.model.OnlineOrder;
import com.food.model.Product;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author juanramon
 */
@ManagedBean(name = "orderCtrl")
@ViewScoped
@RequestScoped
public class OnlineOrderController implements Serializable {

    @Inject
    private DAOOrder daoOrder;
    @Inject
    private DAOUser daoUser;

    private OnlineOrder order;

    private FacesContext facesContext = FacesContext.getCurrentInstance();

    @PostConstruct
    public void init() {
        order = new OnlineOrder();
    }

    public OnlineOrderController() {
    }

    public void createOrder(double total) {

        String id_userr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id_user");
        Long id_user = new Long(Long.parseLong(id_userr));

        this.order.setU(daoUser.getUser(id_user));
        Date date = new Date();

        this.order.setDate(date);

        this.order.setState(this.order.getState());

        this.order.setTotal(total);

        try {
            getDaoOrder().createOrder(this.order);
            FacesContext.getCurrentInstance().addMessage("InitOrder", new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Has comenzado un pedido."));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage("DontInitOrder", new FacesMessage(FacesMessage.SEVERITY_INFO, "InCorrecto", "No has comenzado el pedido."));

        }
    }

    public void addProduct(Product p) {

        this.order.getProducts().add(p);

        System.out.println("--------------------\n");
        createOrder(p.getPrecio());

    }
//    public List<Product> getProductsOrderUser() {
//
//        String id_userr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id_user");
//        Long id_user = new Long(Long.parseLong(id_userr));
//        
//        List<Product> l=daoOrder.getProductsOfOrderU(id_user);
//        return l;
//    }

    public List<OnlineOrder> getOrdersUser() {

        String id_userr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id_user");
        Long id_user = new Long(Long.parseLong("1"));

        List<OnlineOrder> l = daoOrder.getOrdersU(id_user);
        return l;
    }

    public void deleteProduct(Product p) {

        order.getProducts().remove(p);

    }

    /**
     * @return the daoOrder
     */
    public DAOOrder getDaoOrder() {
        return daoOrder;
    }

    /**
     * @param daoOrder the daoOrder to set
     */
    public void setDaoOrder(DAOOrder daoOrder) {
        this.daoOrder = daoOrder;
    }

    /**
     * @return the order
     */
    public OnlineOrder getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(OnlineOrder order) {
        this.order = order;
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

}
