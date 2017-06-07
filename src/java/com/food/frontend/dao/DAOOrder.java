/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.frontend.dao;

import com.food.model.OnlineOrder;
import com.food.model.Product;
import java.io.Serializable;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.food.frontend.interfaces.IUOrders;




/**
 * Jersey REST client generated for REST resource:OnlineOrderREST [orders]<br>
 * USAGE:
 * <pre>
 *        DAOOrder client = new DAOOrder();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author juanramon
 */

@Dependent
@Path("orders")
public class DAOOrder implements IUOrders,Serializable{

    private WebTarget webTarget;
    private Client client;
    private ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    private String baseuri= getServletContext().getInitParameter("BaseUri");

    public DAOOrder() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(getBaseuri()).path("orders");
    }


    public Response createOrder(Object requestEntity) throws ClientErrorException {
        return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
    }

    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<OnlineOrder> getOrdersU(Long id) {
        List<OnlineOrder> orders
                = webTarget
                .path("user/{id}").resolveTemplate("id", id)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<OnlineOrder>>() {
                });
        return orders;
    }

    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Product> getProductsOfOrderU(Long id) {
        List<Product> products
                = webTarget
                .path("products/order/user/{id}").resolveTemplate("id", id)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Product>>() {
                });
        return products;
    }

    public void removeProducts(Long id){
        webTarget.path("products/{id}").resolveTemplate("id", id)
                .request(MediaType.APPLICATION_JSON)
                .delete();
    }

    public void close() {
        client.close();
    }

    /**
     * @return the baseuri
     */
    public String getBaseuri() {
        return baseuri;
    }

    /**
     * @param baseuri the baseuri to set
     */
    public void setBaseuri(String baseuri) {
        this.baseuri = baseuri;
    }

    /**
     * @return the servletContext
     */
    public ServletContext getServletContext() {
        return servletContext;
    }

    /**
     * @param servletContext the servletContext to set
     */
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
    
}
