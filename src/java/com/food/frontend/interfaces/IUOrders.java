/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.frontend.interfaces;

import com.food.model.OnlineOrder;
import com.food.model.Product;
import java.util.List;
import javax.jws.WebService;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

/**
 *
 * @author juanramon
 */
@WebService
public interface IUOrders {
    
    public Response createOrder(Object requestEntity) throws ClientErrorException;
    public List<OnlineOrder> getOrdersU(Long id);
    public List<Product> getProductsOfOrderU(Long id);
    
}
