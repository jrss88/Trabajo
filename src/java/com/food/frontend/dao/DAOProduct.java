/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.frontend.dao;

import com.food.frontend.interfaces.IUProduct;
import com.food.model.Product;

import java.util.List;
import javax.ejb.Stateless;
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

/**
 * Jersey REST client generated for REST resource:ProductREST [products]<br>
 * USAGE:
 * <pre>
        DAOProduct client = new DAOProduct();
        Object response = client.XXX(...);
        // do whatever with response
        client.close();
 </pre>
 *
 * @author juanramon
 */
@Dependent
@Path("products")
public class DAOProduct implements IUProduct{

    private WebTarget webTarget;
    private Client client;
    private ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    private String baseuri= getServletContext().getInitParameter("BaseUri");
    

    public DAOProduct() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(getBaseuri()).path("products");
    }

    public Response createProduct(Object requestEntity) throws ClientErrorException {
        return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
    }

    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Product> getProducts() {
        List<Product> products
                = webTarget
                .path("all")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Product>>() {
                });
        return products;
    }

    public void close() {
        client.close();
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

}
