/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.backend.service;

import com.food.model.PrivateMessage;
import java.net.URI;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author JR
 */
@Stateless
@Path("privateMessages")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class PrivateMessageREST {

    @PersistenceContext(unitName = "TFGFoodPU")
    private EntityManager em;

    @Context
    private UriInfo uriInfo;
    
    public PrivateMessageREST() {
        
    }

    @GET
    public JsonObject ShowMessageMain() {

        return Json.createObjectBuilder().add("EAT NEAR HERE", "Web Service RestFuLL PrivateMessages").build();
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(PrivateMessage entity) {
        em.persist(entity);
        URI uri = uriInfo.getAbsolutePathBuilder().path(entity.getId().toString()).build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, PrivateMessage entity) {
        em.merge(entity);
        em.flush();
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        em.remove(em.merge(find(id)));
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PrivateMessage find(@PathParam("id") Long id) {
        PrivateMessage pm = em.find(PrivateMessage.class, id);
        return pm;
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PrivateMessage> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(PrivateMessage.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @GET
    @Path("sends/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PrivateMessage> showPrivateMessagesSends(@PathParam("id")Long id) {

        List<PrivateMessage> l = em.createQuery("SELECT pm FROM PrivateMessage pm WHERE pm.uEmisor.id=?1", PrivateMessage.class)
                 .setParameter(1,id).getResultList();
        return l;
    }

    @GET
    @Path("received/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PrivateMessage> showPrivateMessagesReceived(@PathParam("id")Long id) {

        List<PrivateMessage> l = em.createQuery("SELECT pm FROM PrivateMessage pm WHERE pm.uReceptor.id=?1", PrivateMessage.class)
                 .setParameter(1,id).getResultList();
        return l;
    }
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
