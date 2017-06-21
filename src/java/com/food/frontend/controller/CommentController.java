/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.frontend.controller;

import com.food.frontend.dao.DAOComment;
import com.food.frontend.dao.DAOUser;
import com.food.model.Comment;
import com.food.model.Product;
import com.food.model.User;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author JR
 */
@Named(value = "commentCtrl")
@ViewScoped
public class CommentController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private DAOComment daoComment;

    @Inject
    private DAOUser daoUser;
    private Comment comment;
    
    private Long idP;

    @PostConstruct
    public void init() {

        comment = new Comment();
        idP=0L;
    }

    public CommentController() {

    }

    public void createComment(ActionEvent ae) {

        String remoteUser = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        User u = daoUser.getUserByName(remoteUser);
        Long id_user = u.getId();
        comment.setU(daoUser.getUser(id_user));

        Product p = (Product) ae.getComponent().getAttributes().get("productForComment");
        comment.setP(p);

        try {
            daoComment.createComment(comment);
            FacesContext.getCurrentInstance().addMessage("CreateComment", new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Has comentado."));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage("DontCreateComment", new FacesMessage(FacesMessage.SEVERITY_INFO, "InCorrecto", "No has comentado."));
        }

    }

    public List<Comment> getCommentsProduct(Long id_product) {

        List<Comment> lc = daoComment.getCommentsProduct(id_product);
        return lc;
    }

    public void listenerComment(ActionEvent ae) {

        idP = (Long) ae.getComponent().getAttributes().get("idProduct");
        
    }

    /**
     * @return the comment
     */
    public Comment getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(Comment comment) {
        this.comment = comment;
    }

    /**
     * @return the idP
     */
    public Long getIdP() {
        return idP;
    }

    /**
     * @param idP the idP to set
     */
    public void setIdP(Long idP) {
        this.idP = idP;
    }

}
