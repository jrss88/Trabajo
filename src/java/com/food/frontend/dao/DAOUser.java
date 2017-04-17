/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.frontend.dao;

import com.food.frontend.interfaces.IUUsers;
import com.food.model.User;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Jersey REST client generated for REST resource:UserREST [users]<br>
 * USAGE:
 * <pre>
 DAOUser client = new DAOUser();
 Object response = client.XXX(...);
 // do whatever with response
 client.close();
 </pre>
 *
 * @author juanramon
 */

@Dependent
@Path("users")
public class DAOUser implements IUUsers{

    private WebTarget webTarget;
    private Client client;
    private ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    private String baseuri= getServletContext().getInitParameter("BaseUri");

    public DAOUser() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(getBaseuri()).path("users");
    }


    public Response createUser(Object requestEntity) throws ClientErrorException {
        return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
    }

    public Response editUser(Object requestEntity, Long id) throws ClientErrorException {
        return webTarget.path("{id}").resolveTemplate("id", id).request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> getUsers() {
        List<User> users
                = webTarget
                .path("all")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<User>>() {
                });
        return users;
    }

    @Consumes(MediaType.APPLICATION_JSON)
    public User getUser(Long id) {
        User u
                = webTarget
                .path("{id}").resolveTemplate("id", id)
                .request(MediaType.APPLICATION_JSON)
                .get(User.class);
        return u;
    }
    @Consumes(MediaType.APPLICATION_JSON)
    public User getUserByName(String name) {
        User u
                = webTarget
                .path("findByName/{name}").resolveTemplate("name", name)
                .request(MediaType.APPLICATION_JSON)
                .get(User.class);
        return u;
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
