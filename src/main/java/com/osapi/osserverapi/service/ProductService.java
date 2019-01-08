package com.osapi.osserverapi.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.osapi.osserverapi.entity.Product;
import com.osapi.osserverapi.repository.ProductRepository;

import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ProductService class
 * Adding notation provided by GraphQL (@GraphQLQuery),
 * helps enable the services to be exposed to GraphQL without modifying the name of method
 */

public class ProductService implements GraphQLQueryResolver{
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    /**
     * Get the list of products in the database
     * @return list of products
     */
    @GraphQLQuery(name = "products")
    public List<Product> products(){
        return productRepository.findAll();
    }

    /**
     * Get the product by the id
     * @param the id of product needed to find
     * @return the product
     */
    public Product getProductById(Long id){
        return productRepository.findOne(id);
    }

    /**
     * Save the product
     * @param product
     * @return the product that has been saved
     */
    public Product save(Product product){
        return productRepository.save(product);
    }

    /**
     * Delete the product
     * @param product
     */
    public void delete(Product product){
        productRepository.delete(product);
    }


    /**
     * Get number of products in the inventory
     * @return count
     */
    public Long count() {
        return productRepository.count();
    }


}
