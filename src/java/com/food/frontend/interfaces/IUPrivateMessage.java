/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.frontend.interfaces;

import com.food.model.PrivateMessage;
import javax.jws.WebService;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

/**
 *
 * @author JR
 */
@WebService
public interface IUPrivateMessage {
    
    public Response create(Object requestEntity) throws ClientErrorException;
    public void edit(Object requestEntity, String id) throws ClientErrorException;
    public PrivateMessage getPrivateMessage(Long id);
    
}
