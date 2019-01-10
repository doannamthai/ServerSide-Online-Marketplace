package com.osapi.osserverapi.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.osapi.osserverapi.entity.ShoppingCart;
import com.osapi.osserverapi.exception.CartNotFoundException;
import com.osapi.osserverapi.repository.ShoppingCartRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ShoppingCartQuery implements GraphQLQueryResolver {
    private ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartQuery(ShoppingCartRepository shoppingCartRepository){
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public List<ShoppingCart> shopping_carts(){
        return shoppingCartRepository.findAll();
    }

    /**
     * Return shopping cart given cart id
     * @param cart_id
     * @return the shopping cart that has given cart id
     */
    public List<ShoppingCart> shopping_carts(Long cart_id){
        if (cart_id == null)
            return shoppingCartRepository.findAll();

        ShoppingCart shoppingCart = shoppingCartRepository.findOne(cart_id);

        if (shoppingCart == null)
            throw new CartNotFoundException("There is no such shopping cart with given cart_id", cart_id);
        return Arrays.asList(shoppingCart);
    }

}
