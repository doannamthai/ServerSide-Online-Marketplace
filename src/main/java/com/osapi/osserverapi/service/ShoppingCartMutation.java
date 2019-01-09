package com.osapi.osserverapi.service;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.osapi.osserverapi.entity.*;
import com.osapi.osserverapi.repository.ProductRepository;
import com.osapi.osserverapi.repository.ShoppingCartRepository;
import com.osapi.osserverapi.utilities.Utilities;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Component
public class ShoppingCartMutation implements GraphQLMutationResolver {

    private ShoppingCartRepository shoppingCartRepository;
    private ProductRepository productRepository;

    public ShoppingCartMutation(ProductRepository productRepository, ShoppingCartRepository shoppingCartRepository){
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
    }

    /**
     * Create a new cart with the user_id given
     * Automatically generate cart_id
     * @param customer_id (optional)
     * @return the new generated shopping cart
     */
    public ShoppingCart new_cart(Long customer_id){
        ShoppingCart cart = new ShoppingCart();
        // Set the user id for the cart (optional)
        cart.setCustomer_id(customer_id);
        // Set the cart id to the current time stamp
        cart.setCart_id(System.currentTimeMillis());
        shoppingCartRepository.save(cart);
        return cart;
    }

    /**
     * Delete a cart with the cart_id given
     * @param cart_id
     * @return true if the cart has been deleted, false otherwise
     */
    public Boolean delete_cart(Long cart_id){
        // If there is no cart's id, then return false
        // Or if there is no such cart_id
        if (cart_id == null || !shoppingCartRepository.exists(cart_id))
            return false;
        // Otherwise, perform deleting operation and return true
        shoppingCartRepository.delete(cart_id);
        return true;
    }

    /**
     * Add the given list of purchasing products to the cart
     * @param purchasingProducts
     * @param cart_id
     * @return the updated shopping cart
     */
    public ShoppingCart add_to_cart(List<PurchasingProduct> purchasingProducts, Long cart_id){

        // Get the cart associated with the id given
        ShoppingCart shoppingCart = shoppingCartRepository.findOne(cart_id);
        // If cart is not found, return
        if (shoppingCart == null)
            return null;
        // Otherwise add products to the shopping cart
        if (shoppingCart.getItems() == null)
            shoppingCart.setItems(new HashMap<>());

        for (PurchasingProduct purchasingProduct : purchasingProducts){
            // Retrieve data of purchasing product
            Product product = productRepository.findOne(purchasingProduct.getItem_id());
            // If there is no such product, skip
            if (product == null)
                continue;
            LineProduct lineProduct;

            Long count = purchasingProduct.getCount();
            BigDecimal sub_total_price = product.getPrice().multiply(new BigDecimal(count));

            // If this cart has already contained the same item
            if (shoppingCart.getMapLineProduct().containsKey(product.getItem_id())){
                lineProduct = shoppingCart.getMapLineProduct().get(product.getItem_id());
                // Update count and total price for this item
                lineProduct.setCount(count + lineProduct.getCount());
                lineProduct.setTotal_price(lineProduct.getTotal_price().add(sub_total_price));
            } else {
                lineProduct = new LineProduct();
                // Set attributes for line product
                lineProduct.setItem_id(product.getItem_id());
                lineProduct.setPrice(product.getPrice());
                lineProduct.setTitle(product.getTitle());
                lineProduct.setCount(count);
                lineProduct.setTotal_price(sub_total_price);
            }

            // Check whether this item should be added
            Boolean added_to_cart = product.getInventory_count() < lineProduct.getCount() || lineProduct.getCount() <= 0 ? false : true;
            lineProduct.setAdded_to_cart(added_to_cart);
            shoppingCart.getMapLineProduct().put(lineProduct.getItem_id(), lineProduct);

        }
        this.updateAttributes(shoppingCart);
        // Update the shopping cart to the server
        shoppingCartRepository.save(shoppingCart);
        return shoppingCart;
    }

    /**
     * Delete a list of items from the cart with id given
     * @param purchasingProducts
     * @param cart_id
     * @return the updated shopping cart
     */
    public ShoppingCart remove_from_cart(List<PurchasingProduct> purchasingProducts, Long cart_id){
        // Get the cart associated with the given id
        ShoppingCart shoppingCart = shoppingCartRepository.findOne(cart_id);
        // If cart is not found,
        // Or the cart is empty, then return
        if (shoppingCart == null || shoppingCart.getItems() == null || shoppingCart.getItems().isEmpty())
            return shoppingCart;
        // Otherwise remove products from the shopping cart
        for (PurchasingProduct purchasingProduct: purchasingProducts){
            if (!shoppingCart.getMapLineProduct().containsKey(purchasingProduct.getItem_id()))
                continue;
            // Remove the line product associated with the id given
            shoppingCart.getMapLineProduct().remove(purchasingProduct.getItem_id());
        }
        this.updateAttributes(shoppingCart);
        // Update the shopping cart repository
        shoppingCartRepository.save(shoppingCart);
        return shoppingCart;
    }

    /**
     * Update the item count in shopping cart
     * @param purchasingProducts
     * @param cart_id
     * @return new updated shopping cart
     */
    public ShoppingCart update_cart(List<PurchasingProduct> purchasingProducts, Long cart_id){
        // Get the cart associated with the given id
        ShoppingCart shoppingCart = shoppingCartRepository.findOne(cart_id);
        // If cart is not found,
        // Or the cart, or the purchasing list is empty, then return
        if (shoppingCart == null || shoppingCart.getItems() == null || shoppingCart.getItems().isEmpty() || purchasingProducts.isEmpty())
            return shoppingCart;

        // Otherwise update the item count to the current item count of the shopping cart
        for (PurchasingProduct purchasingProduct : purchasingProducts){

            // If there is no such item in the cart, skip
            if (!shoppingCart.getMapLineProduct().containsKey(purchasingProduct.getItem_id()))
                continue;
            // Validate the new item count
            // If the new count is negative, skip
            if (purchasingProduct.getCount() < 0)
                continue;

            Product product = productRepository.findOne(purchasingProduct.getItem_id());
            // If there is no such item in the inventory, skip
            if (product == null)
                continue;
            LineProduct lineProduct = shoppingCart.getMapLineProduct().get(purchasingProduct.getItem_id());

            Long new_count = purchasingProduct.getCount();
            BigDecimal new_sub_total_price = lineProduct.getPrice().multiply(new BigDecimal(purchasingProduct.getCount()));

            // Update to the new count and new total price attributes
            lineProduct.setCount(purchasingProduct.getCount());
            lineProduct.setTotal_price(new_sub_total_price);

            // Check whether this item should be added
            Boolean added_to_cart = product.getInventory_count() < new_count || new_count <= 0 ? false : true;
            lineProduct.setAdded_to_cart(added_to_cart);

            // Update the map in the shopping cart
            shoppingCart.getMapLineProduct().put(lineProduct.getItem_id(), lineProduct);
        }

        this.updateAttributes(shoppingCart);
        // Update the shopping cart repository
        shoppingCartRepository.save(shoppingCart);
        return shoppingCart;
    }


    /**
     * Update the inventory and return the order
     * @param cart_id
     * @return the purchased order
     */
    public Order purchase_cart(Long cart_id){
        Order order = new Order();
        if (cart_id == null || shoppingCartRepository.findOne(cart_id) == null)
            return order;

        ShoppingCart shoppingCart = shoppingCartRepository.findOne(cart_id);

        for (LineProduct lineProduct : shoppingCart.getItems()){
            // Remove all items that exceed inventory count
            if (!lineProduct.getAdded_to_cart())
                shoppingCart.getMapLineProduct().remove(lineProduct.getItem_id());
            else {
                // Find the product in the inventory
                Product product = productRepository.findOne(lineProduct.getItem_id());
                // Update the inventory count
                product.setInventory_count(product.getInventory_count() - lineProduct.getCount());
                // Save this product to the server
                productRepository.save(product);
            }
        }
        // Save purchased shopping cart to the order
        order.setPurchased_cart(shoppingCart);
        order.setCustomer_id(shoppingCart.getCustomemr_id());
        order.setOrder_date(Utilities.getCurrentDate());
        order.setOrder_time(Utilities.getCurrentTime());
        order.setOrder_id(System.currentTimeMillis());

        // Delete this cart from server
        shoppingCartRepository.delete(shoppingCart.getCart_id());
        return order;
    }

    /**
     * Update shopping cart attributes (total_price and count)
     * @param cart
     */
    private void updateAttributes(ShoppingCart cart){
        if (cart == null)
            return;
        BigDecimal total_price = new BigDecimal(0);
        Long item_count = Long.valueOf(0);
        for (LineProduct lineProduct : cart.getItems()){
            if (lineProduct.getAdded_to_cart()){
                total_price = total_price.add(lineProduct.getTotal_price());
                item_count = item_count + lineProduct.getCount();
            }
        }
        cart.setItem_count(item_count);
        cart.setTotal_price(total_price);
    }

}
