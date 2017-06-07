/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.backend.service;

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
@Path("users")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class UserREST {

    @PersistenceContext(unitName = "TFGFoodPU")
    private EntityManager em;
    @Context
    private UriInfo uriInfo;

    @GET
    public JsonObject ShowMessageMain() {

        return Json.createObjectBuilder().add("EAT NEAR HERE", "Web Service RestFuLL Users").build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(User entity) {
        em.persist(entity);
        URI uri = uriInfo.getAbsolutePathBuilder().path(entity.getId().toString()).build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void edit(@PathParam("id") Long id, User entity) {
        em.merge(entity);
        em.flush();
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(User.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User find(@PathParam("id") Long id) {
        User u = em.find(User.class, id);
        return u;
    }

    @GET
    @Path("findByName/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public User findByName(@PathParam("name") String name) {
        User u = em.createQuery("SELECT u FROM User u WHERE u.name=?1", User.class).setParameter(1, name).getSingleResult();
        return u;
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        em.remove(em.merge(find(id)));
    }

    protected EntityManager getEntityManager() {
        return em;
    }

}
