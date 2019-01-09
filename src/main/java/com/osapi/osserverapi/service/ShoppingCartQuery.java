package com.osapi.osserverapi.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.osapi.osserverapi.entity.ShoppingCart;
import com.osapi.osserverapi.repository.ShoppingCartRepository;
import org.springframework.stereotype.Component;

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

    public ShoppingCart shopping_carts(Long cart_id){
        return shoppingCartRepository.findOne(cart_id);
    }

}
