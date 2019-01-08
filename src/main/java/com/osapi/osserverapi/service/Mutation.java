package com.osapi.osserverapi.service;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.osapi.osserverapi.entity.Product;
import com.osapi.osserverapi.entity.PurchasingProduct;
import com.osapi.osserverapi.entity.PurchasingProductOut;
import com.osapi.osserverapi.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Mutation implements GraphQLMutationResolver {

    private ProductRepository productRepository;

    public Mutation(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    /**
     * Purchase a list of items.
     * If the purchasing item meet the condition then update the product's count
     * @param purchasingProducts
     * @return the list of purchasing products output
     */
    public List<PurchasingProductOut> purchase(List<PurchasingProduct> purchasingProducts){
        List<PurchasingProductOut> result = new ArrayList<>();

        for (PurchasingProduct purchasingProduct : purchasingProducts){
            // Find the product that needs to be updated
            Product product = productRepository.findOne(purchasingProduct.getId());

            if (product != null){
                PurchasingProductOut purchasingProductOut = new PurchasingProductOut(purchasingProduct);
                // If the current purchasing product has positive inventory count
                // And the quantity after the purchase is not negative
                // Then set purchasable to choose and update the count
                if (product.getInventory_count() > 0 &&
                        product.getInventory_count() - purchasingProduct.getQuantity() >= 0){
                    purchasingProductOut.setPurchased(true);
                    product.setInventory_count(product.getInventory_count() - purchasingProduct.getQuantity());
                    // Update the current product to repository
                    productRepository.save(product);
                }
                // Reject unavailable products otherwise
                // Add the output to the result
                result.add(purchasingProductOut);
            }

        }
        return result;
    }
}
