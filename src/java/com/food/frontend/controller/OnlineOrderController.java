/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.frontend.controller;

import com.food.frontend.dao.DAOOrder;
import com.food.frontend.dao.DAOProduct;
import com.food.frontend.dao.DAOUser;
import com.food.model.OnlineOrder;
import com.food.model.Product;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author juanramon
 */
@Named(value = "orderCtrl")
@ViewScoped
public class OnlineOrderController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private DAOOrder daoOrder;
    @Inject
    private DAOUser daoUser;
    
    @Inject
    private DAOProduct daoProduct;

    private OnlineOrder order;

    @PostConstruct
    public void init() {

        order = new OnlineOrder(); //init order on entering view

    }

    public OnlineOrderController() {

        order = new OnlineOrder();
    }

    public void createOrder() {

        //String id_userr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id_user");
        Long id_user = new Long(Long.parseLong("1"));

//        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//        String sessionId = session.getId();
//        Long id_user= new Long(Long.parseLong(sessionId));
        
        order.setU(daoUser.getUser(id_user));

        try {
            daoOrder.createOrder(order);
            FacesContext.getCurrentInstance().addMessage("InitOrder", new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Has comenzado un pedido."));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage("DontInitOrder", new FacesMessage(FacesMessage.SEVERITY_INFO, "InCorrecto", "No has comenzado el pedido."));
        }
        order.getProducts().clear();
    }

    public void addProduct(ActionEvent event) {

        
        System.out.print("asdasdasdasda sasdasdasd as dasdasdasdasd asd as dasd ");
        //Long idproduct = (Long) e.getComponent().getAttributes().get("idProduct");
        Long idProduct = (Long) event.getComponent().getAttributes().get("idProduct");
        
        Product p = daoProduct.getProduct(idProduct);
        order.getProducts().add(p); //aquí lp devolvía null y fallaba add
        
        if (order == null) {
            createOrder();
        }
        else{
            setPriceToTotal(p.getPrecio());
        }

    }
    public void setPriceToTotal(double price){
    
        order.setTotal(order.getTotal()+price);
    
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
        Double newTotal = order.getTotal() - p.getPrecio();
        order.setTotal(newTotal);
        

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
     * @return the daoProduct
     */
    public DAOProduct getDaoProduct() {
        return daoProduct;
    }

    /**
     * @param daoProduct the daoProduct to set
     */
    public void setDaoProduct(DAOProduct daoProduct) {
        this.daoProduct = daoProduct;
    }

}
