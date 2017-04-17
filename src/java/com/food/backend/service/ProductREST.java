/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.backend.service;

import com.food.model.Product;
import com.food.model.User;
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
 * @author juanramon
 */
@Stateless
@Path("products")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ProductREST {

    @PersistenceContext(unitName = "TFGFoodPU")
    private EntityManager em;
    @Context
    private UriInfo uriInfo;

    public ProductREST() {

    }

    @GET
    public JsonObject ShowMessageMain() {

        return Json.createObjectBuilder().add("EAT NEAR HERE", "Web Service RestFuLL Products").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Product entity) {
        getEntityManager().persist(entity);
        URI uri = uriInfo.getAbsolutePathBuilder().path(entity.getId().toString()).build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void edit(@PathParam("id") Long id, Product entity) {
        getEntityManager().merge(entity);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product find(@PathParam("id") Long id) {
        Product p = getEntityManager().find(Product.class, id);
        return p;
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        getEntityManager().remove(id);
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Product.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @GET
    @Path("products/user/{id_user}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> muestraProductosUser(@PathParam("id_user")Long id_user) {

        
        List<Product> l = em.createQuery("SELECT p FROM Product p WHERE p.uPublish.id=?1", Product.class)
                 .setParameter(1,id_user).getResultList();
        return l;
    }
    
    protected EntityManager getEntityManager() {
        return em;
    }

}
