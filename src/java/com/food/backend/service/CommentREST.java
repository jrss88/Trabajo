/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.backend.service;

import com.food.model.Comment;
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
@Path("comments")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class CommentREST  {

    @PersistenceContext(unitName = "TFGFoodPU")
    private EntityManager em;
    @Context
    private UriInfo uriInfo;
    
    public CommentREST() {
        
    }

    @GET
    public JsonObject ShowMessageMain() {

        return Json.createObjectBuilder().add("TIPICAL FOOD CONNECTION", "Web Service RestFuLL Comments").build();
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response create(Comment entity) {
        em.persist(entity);
        URI uri = uriInfo.getAbsolutePathBuilder().path(entity.getId().toString()).build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Comment entity) {
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
    public Comment find(@PathParam("id") Long id) {
        Comment c = em.find(Comment.class, id);
        return c;
    }

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Comment> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Comment.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @GET
    @Path("product/{id_product}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> showCommentProduct(@PathParam("id_product")Long id_product) {

        
        List<Comment> lc = em.createQuery("SELECT c FROM Comment c WHERE c.p.id=?1", Comment.class)
                 .setParameter(1,id_product).getResultList();
        return lc;
    }
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
