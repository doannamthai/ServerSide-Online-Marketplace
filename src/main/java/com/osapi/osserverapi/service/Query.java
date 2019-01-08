package com.osapi.osserverapi.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.osapi.osserverapi.entity.FilterList;
import com.osapi.osserverapi.entity.Product;
import com.osapi.osserverapi.repository.ProductRepository;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {
    private ProductRepository productRepository;

    public Query(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> products() {
        return productRepository.findAll();
    }

    public List<Product> products(FilterList filterList){
        return FilterService.filter(filterList, productRepository);
    }

    public Long count() {
        return productRepository.count();
    }

}