package com.osapi.osserverapi.service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.osapi.osserverapi.entity.FilterList;
import com.osapi.osserverapi.entity.ItemsData;
import com.osapi.osserverapi.entity.Product;
import com.osapi.osserverapi.exception.ProductNotFoundException;
import com.osapi.osserverapi.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductQuery implements GraphQLQueryResolver {
    private ProductRepository productRepository;
    private FilterService filterService;
    public ProductQuery(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.filterService = new FilterService(productRepository);
    }

    /**
     * Get all products from database and apply filter
     * @param filterList
     * @return the items data, contains list of products and count
     */
    public ItemsData find_products(FilterList filterList){
        filterService.setFilter(filterList);
        ItemsData items = new ItemsData();
        items.setProducts(filterService.filter());
        items.setCount(filterService.getCount());
        return items;
    }

    /**
     * Get the product that has given id
     * @param item_id
     * @return the product has given id
     */
    public Product find_product_by_id(Long item_id){

        if (item_id == null){
            throw new ProductNotFoundException("item_id is not valid", item_id);
        }

        Product product = productRepository.findOne(item_id);
        if (product == null){
            throw new ProductNotFoundException("There is no such product with given item_id", item_id);
        }

        return product;
    }

}
