package com.osapi.osserverapi.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.osapi.osserverapi.entity.FilterList;
import com.osapi.osserverapi.entity.Product;
import com.osapi.osserverapi.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductQuery implements GraphQLQueryResolver {
    private ProductRepository productRepository;
    private FilterList filterList;
    public ProductQuery(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Get all products from database and apply filter
     * @param filterList
     * @return the filtered list of products
     */
    public List<Product> products(FilterList filterList){
        this.filterList = filterList;
        return FilterService.filter(filterList, productRepository);
    }

    /**
     * Get the inventory count of products with adjustment to the filter
     * @return
     */
    public Long count() {
        return productRepository.count();
    }
}
