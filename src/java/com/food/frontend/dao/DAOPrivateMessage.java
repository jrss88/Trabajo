/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.frontend.dao;

import com.food.frontend.interfaces.IUPrivateMessage;
import com.food.model.PrivateMessage;
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
 * Jersey REST client generated for REST resource:PrivateMessageREST
 * [privateMessages]<br>
 * USAGE:
 * <pre>
 *        DAOPrivateMessage client = new DAOPrivateMessage();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author JR
 */
@Dependent
@Path("privateMessages")
public class DAOPrivateMessage implements Serializable,IUPrivateMessage{

    private WebTarget webTarget;
    private Client client;
    private ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    private String baseuri= getServletContext().getInitParameter("BaseUri");

    public DAOPrivateMessage() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(getBaseuri()).path("privateMessages");
    }

    public Response create(Object requestEntity) throws ClientErrorException {
        return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
    }
    
    public void edit(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    @Consumes(MediaType.APPLICATION_JSON)
    public PrivateMessage getPrivateMessage(Long id) {
        PrivateMessage pm
                = webTarget
                .path("{id}").resolveTemplate("id", id)
                .request(MediaType.APPLICATION_JSON)
                .get(PrivateMessage.class);
        return pm;
    }

    @Consumes(MediaType.APPLICATION_JSON)
    public List<PrivateMessage> getPrivateMessagesSends(Long id) {
        List<PrivateMessage> privateMessagesSends
                = webTarget
                .path("sends/{id}").resolveTemplate("id", id)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<PrivateMessage>>() {
                });
        return privateMessagesSends;
    }
    
    @Consumes(MediaType.APPLICATION_JSON)
    public List<PrivateMessage> getPrivateMessagesReceived(Long id) {
        List<PrivateMessage> privateMessagesReceived
                = webTarget
                .path("received/{id}").resolveTemplate("id", id)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<PrivateMessage>>() {
                });
        return privateMessagesReceived;
    }
    

    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
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
