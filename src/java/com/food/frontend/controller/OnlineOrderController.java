/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.frontend.controller;

import com.food.frontend.dao.DAOOrder;
import com.food.frontend.dao.DAOUser;
import com.food.frontend.interfaces.IU;
import com.food.model.OnlineOrder;
import com.food.model.Product;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;



/**
 *
 * @author juanramon
 */
@Named(value="orderCtrl")
@ViewScoped
public class OnlineOrderController implements Serializable{

    private static final long serialVersionUID = 1L;
   
    @Inject 
    private DAOOrder daoOrder;
    @Inject
    private DAOUser daoUser;

    private OnlineOrder order=new OnlineOrder();

   

    @PostConstruct
    public void init() {

      

    }

    public OnlineOrderController() {
   
    }

    public void createOrder(double total) {

      
        //String id_userr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id_user");
        Long id_user = new Long(Long.parseLong("1"));
        
//        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//        String sessionId = session.getId();
//        Long id_user= new Long(Long.parseLong(sessionId));
        
     
        order.setTotal(total);
        order.setU(daoUser.getUser(id_user));
        Date date = new Date();
        order.setDate(date);
        order.setState(order.getState());

        try {
            daoOrder.createOrder(order);
            FacesContext.getCurrentInstance().addMessage("InitOrder", new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Has comenzado un pedido."));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage("DontInitOrder", new FacesMessage(FacesMessage.SEVERITY_INFO, "InCorrecto", "No has comenzado el pedido."));

        }
    }

    public void addProduct(Product p) {

        order.getProducts().add(p);
        createOrder(3.3);

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


}
