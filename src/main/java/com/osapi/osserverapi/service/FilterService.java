package com.osapi.osserverapi.service;

import com.osapi.osserverapi.entity.FilterList;
import com.osapi.osserverapi.entity.Product;
import com.osapi.osserverapi.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * FilterService class, helps filter data
 */
public class FilterService {

    private Long count;

    private FilterList filter;
    private ProductRepository productRepository;

    public FilterService(FilterList filter, ProductRepository productRepository){
        this.filter = filter;
        this.productRepository = productRepository;
        this.count = productRepository.count();
    }

    public FilterService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public void setFilter(FilterList filterList){
        this.filter = filterList;
    }

    public FilterList getFilter(){
        return filter;
    }


    public List<Product> filter(){
        // Get the list of products
        List<Product> products = productRepository.findAll();
        // Filter by conditions
        return buildFilter(filter, products);
    }

    /**
     * Filter the list of products with given properties
     * @param filter
     * @param products
     * @return the filtered list
     */
    private List<Product> buildFilter(FilterList filter, List<Product> products){
        if (filter == null){
            count = productRepository.count();
            return products;
        }
        String titleContains = filter.getTitleContains();
        BigDecimal priceLte = filter.getPriceLte();
        BigDecimal priceGte = filter.getPriceGte();
        Long countLte = filter.getCountLte();
        Long countGte = filter.getCountGte();

        // Check whether some properties need to be filtered
        boolean filterTitle = titleContains == null || titleContains.replace(" ", "").length() == 0 ? false : true;
        boolean filterPriceLte = priceLte == null ? false : true;
        boolean filterPriceGte = priceGte == null ? false : true;
        boolean filterCountLte = countLte == null ? false : true;
        boolean filterCountGte = countGte == null ? false : true;

        List<Product> filteredList = new ArrayList<>();

        for (Product product : products){
            if (filterTitle){
                if (!product.getTitle().contains(titleContains))
                    continue;
            }

            if (filterPriceLte){
                if (product.getPrice().compareTo(priceLte) > 0)
                    continue;
            }

            if (filterPriceGte){
                if (product.getPrice().compareTo(priceGte) < 0)
                    continue;
            }

            if (filterCountLte){
                if (product.getInventory_count() > countLte)
                    continue;
            }

            if (filterCountGte){
                if (product.getInventory_count() < countGte)
                    continue;
            }

            // If the product did satisfy the conditions
            // Add the product to the filtered list
            filteredList.add(product);

        }
        count = Long.valueOf(filteredList.size());
        return filteredList;
    }

    public Long getCount(){
        return count;
    }

}
