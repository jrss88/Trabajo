/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.frontend.interfaces;

import com.food.model.User;
import java.util.List;
import javax.jws.WebService;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

/**
 *
 * @author juanramon
 */
@WebService
public interface IUUsers {
    
    public Response create_JSON(Object requestEntity) throws ClientErrorException;
    public Response edit_JSON(Object requestEntity, Long id) throws ClientErrorException;
    public List<User> getUsers();
    public User getUser(Long id);
}
