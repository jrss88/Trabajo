/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.frontend.interfaces;

import com.food.model.Comment;
import java.util.List;
import javax.jws.WebService;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

/**
 *
 * @author JR
 */
@WebService
public interface IUComment {
    
    public Response createComment(Object requestEntity) throws ClientErrorException;
    public List<Comment> getCommentsProduct(Long id);
}
