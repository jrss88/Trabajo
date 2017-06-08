/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.backend.service;

import com.food.model.OnlineOrder;
import com.food.model.Product;
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
@Path("orders")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class OnlineOrderREST {

    @PersistenceContext(unitName = "TFGFoodPU")
    private EntityManager em;

    @Context
    private UriInfo uriInfo;
    
    public OnlineOrderREST() {
        
    }
    
    @GET
    public JsonObject ShowMessageMain() {

        return Json.createObjectBuilder().add("EAT NEAR HERE", "Web Service RestFuLL Orders").build();
    }
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(OnlineOrder entity) {
        em.persist(entity);
        URI uri = uriInfo.getAbsolutePathBuilder().path(entity.getId().toString()).build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void edit(@PathParam("id") Long id, OnlineOrder entity) {
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
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public OnlineOrder find(@PathParam("id") Long id) {
        OnlineOrder o = em.find(OnlineOrder.class, id);
        return o;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<OnlineOrder> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(OnlineOrder.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @GET
    @Path("user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<OnlineOrder> showOrderUser(@PathParam("id")Long id) {

        List<OnlineOrder> lo = em.createQuery("SELECT o FROM OnlineOrder o WHERE o.u.id=?1", OnlineOrder.class)
                 .setParameter(1,id).getResultList();
        return lo;
    }
    
    

    @GET
    @Path("products/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getProductsOrderUser(@PathParam("id")Long id) {

        
        List<Product> lo = em.createQuery("SELECT p FROM OnlineOrder o.products p WHERE o.u.id=?1", Product.class)
                 .setParameter(1,id).getResultList();
        return lo;
    }

    
    @GET
    @Path("received/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<OnlineOrder> showOrderExternalUser(@PathParam("id")Long id) {

        List<OnlineOrder> lo = em.createQuery("SELECT o FROM OnlineOrder o WHERE o.u_saler.id=?1", OnlineOrder.class)
                 .setParameter(1,id).getResultList();
        return lo;
    }

    protected EntityManager getEntityManager() {
        return em;
    }
    
}