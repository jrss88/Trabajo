/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.frontend.dao;

import com.food.frontend.interfaces.IUComment;
import com.food.model.Comment;
import java.io.Serializable;
import java.util.List;
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
 * Jersey REST client generated for REST resource:CommentREST [comments]<br>
 * USAGE:
 * <pre>
 *        DAOComment client = new DAOComment();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author JR
 */
@Dependent
@Path("comments")
public class DAOComment implements IUComment,Serializable{

    private WebTarget webTarget;
    private Client client;
    private ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    private String baseuri= getServletContext().getInitParameter("BaseUri");

    public DAOComment() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(getBaseuri()).path("comments");
    }

     public Response createComment(Object requestEntity) throws ClientErrorException {
        return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
    }
    

    @Consumes(MediaType.APPLICATION_JSON)
    public List<Comment> getCommentsProduct(Long id_product) {
        List<Comment> comments
                = webTarget
                .path("product/{id_product}").resolveTemplate("id_product", id_product)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Comment>>() {
                });
        return comments;
    }
    
    public void close() {
        getClient().close();
    }

    /**
     * @return the webTarget
     */
    public WebTarget getWebTarget() {
        return webTarget;
    }

    /**
     * @param webTarget the webTarget to set
     */
    public void setWebTarget(WebTarget webTarget) {
        this.webTarget = webTarget;
    }

    /**
     * @return the client
     */
    public Client getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(Client client) {
        this.client = client;
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
