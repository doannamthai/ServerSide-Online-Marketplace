
# Web API For Online Marketplace 
> A basic serverside web api for online marketplace

Language and frameworks used in the project:
- Java 10
- GraphQL
- Spring Boot

Demo: [GraphiQL on Heroku](https://serverside-os-api.herokuapp.com/graphiql) - A graphical interactive in-browser IDE for GraphQL
## Getting Started
You have 3 ways to test or run this project:
 1. Use the GraphiQL IDE installed on demo's link,
 2. Clone this repository and run locally. It opens port `8000` on localhost,
 3. Or by using `HTTP Requests` to server https://serverside-os-api.herokuapp.com/graphql.
 
 Some examples to test the API:
 1. [List all products and display the count](#list-all-products-and-display-the-count)
 2. [List only available products](#list-only-available-products)
 3. [Create a new cart](#create-a-new-cart)
 4. [Add items to the shopping cart](#add-items-to-the-exist-shopping-cart)
 
 ## How to buy items ?
 
  1. Fetch products and remember the id of the item you like.
  2. Create a new shopping cart.
  3. Add the item with the count to the shopping cart.
  4. Update item's count if it is necessary.
  5. Purchase the cart and enjoy!
 
## Entities
 *(\* indicates optional fields)*
 ### Product
 Includes 2 queries:
  - `find_products(filter: FilterList*): ItemsData` returns the filtered list of products with the count from the database or returns all the products if the filter is not declared.
  
  - `find_product_by_id(item_id: ID): Product` returns the product associated with the given `item_id`. It throws `ProductNotFoundException` if there is no such product.
  
 ### Shopping Cart
 Includes 1 query and 6 mutations
  #### Query
  - `shopping_carts(cart_id: ID*): [ShoppingCart]` returns the shopping cart associated with the given cart_id or all the carts if `cart_id` is not declared.
  #### Mutation
  - `new_cart(customer_id: ID*): ShoppingCart` creates a new shopping cart with the given `customer_id` and returns the new `ShoppingCart`.
  
  - `delete_cart(cart_id: ID): Boolean` deletes the shopping cart that has the given `cart_id` and returns true if the cart has been deleted. It throws `CartNotFoundException` if there is no such cart.
  
  - `add_to_cart(purchasing_products: [PurchasingProduct], cart_id: ID): ShoppingCart` takes a list of `PurchasingProduct` and the `cart_id` as parameters, adds the given list of products to the shopping cart and returns the new updated cart. If the `item_id` is already in the shopping cart, increase the count or insert a new item, otherwise. If the amount of any item exceeds the `inventory_count`, the `added_to_cart` property of that item is set to `false` and the shopping cart will **not** count the item. It throws `CartNotFoundException` if there is no such cart. 
  
  - `remove_from_cart(purchasing_products: [PurchasingProduct], cart_id: ID): ShoppingCart `takes a list of `PurchasingProduct` and the `cart_id` as parameters, removes all the items in the given list from shopping cart and returns the new updated cart. It throws `CartNotFoundException` if there is no such cart or throws `CartIsEmptyException` if the cart is empty. 
  
  - `update_cart(purchasing_products: [PurchasingProduct], cart_id: ID): ShoppingCart` takes a list of `PurchasingProduct` and the `cart_id` as parameters, updates all the `count` of items in the shopping cart to the new `count` of items in the given list (as long as they match the `item_id`) and returns the the new updated cart. It throws `CartNotFoundException` if there is no such cart.
  
  - `purchase_cart(cart_id: ID): Order` updates the `inventory_count` of the product in the shopping cart, only processes any item that has `added_to_cart` attribute is `true`. It returns the created `Order`. It throws `CartNotFoundException` if there is no such cart.
  
## Definition of models
#### Product
 - Type: `Output`
 - `item_id : Long`: The id of the item.
 - `title: String`: The title of the item.
 - `price: BigDecimal`: The price of the item.
 - `inventory_count: Long`: The count of the item.
 
#### FilterList
 - Type: `Input`
 - `title_contains: String*`: The title that contains an user-defined string.
 - `price_gte: BigDecimal*`: The price that is greater than or equal to.
 - `price_lte: BigDecimal*`: The price that is less than or equal to. 
 - `count_gte: Long*`: The inventory count that is greater than or equal to.
 - `count_lte: Long*`: The inventory count that is less than or equal to.
 
#### PurchasingProduct
 - Type: `Input`
 - `item_id: ID`: The id of the purchasing item.
 - `count: Long`: The count of the purchasing item.
 - `title: String*`: The title of the purchasing item.
 
#### ItemsData
 - Type: `Output`
 - `products: [Product]`: The list of products.
 - `count: Long`: The number of items in the list.
 
#### ShoppingCart
 - Type: `Output`
 - `cart_id: ID`: The id of the shopping cart.
 - `customer_id: ID`: The id of customer belongs to this shopping cart.
 - `items:  [LineProduct]`: The list of line items.
 - `total_price: BigDecimal`: The total price of all items in the cart.
 - `item_count: Long`: The number of distinguishable items in the cart.
 
#### LineProduct
 - Type: `Output`
 - `item_id: ID`: The id of the line item.
 - `title: String`: The title of the line item.
 - `count: Long`: The count of the line item.
 - `price: BigDecimal`: The price of the line item.
 - `total_price: BigDecimal`: The total price (count* price) of the line item.
 - `added_to_cart: Boolean`: The boolean atribute indicates whether the item has been added to cart.
 
#### Order 
 - Type: `Output`
 - `order_id: ID`: The id of this order.
 - `customer_id: ID`: The customer's id belongs to this order (if it has one).
 - `purchased_cart: ShoppingCart`: The shopping cart that has been purchased.
 - `order_date: String`: The date the order is placed.
 - `order_time: String`: The time the order is placed.

## Examples

 For convenience, all examples for `HTTP Requests` will be demonstrated using `POST` method to server `https://serverside-os-api.herokuapp.com/graphql` or your localhost (if you have already cloned this repository) on `localhost:8000/graphql`
 
 Note that, for output type models, you can display one or many attributes by listing the name of attribute. 
 
 #### List all products and display the count: 
 #### On GraphiQL IDE:
 ```
 query{
    find_products{
    count
    products{
      item_id
      title
      inventory_count
      price
    }
  }
}
 ```
#### HTTP Requests
```
{
  "query": "{find_products { count products { item_id title inventory_count price } } }"
}
 ```

 #### List only available products
 #### On GraphiQL IDE:
 ```
 query{
    find_products(filter: {count_gte: 1}){
    products{
      item_id
      title
      inventory_count
      price
    }
  }
}
 ```
#### HTTP Requests

```
{
  "query": "{find_products(filter: {count_gte: 1}) { products { item_id title inventory_count price } } }"
}
 ```
 
 #### Create a new cart

 #### On GraphiQL IDE:
 ```
mutation {
  new_cart(customer_id: 2){
    cart_id
    customer_id
    item_count
    items {
      item_id
      title
    }
    item_count
  }
}
 ```
#### HTTP Requests

```
{
  "mutation": "{new_cart(customer_id: 2) { cart_id customer_id items { item_id title} item_count } }"
}
 ```
 
 #### Add items to the exist shopping cart

 #### On GraphiQL IDE:
 ```
mutation {
  add_to_cart(purchasing_products: {item_id: 1, count: 4}, cart_id: 2){
   	cart_id
    items {
      item_id
      title
    	 count
      price
      total_price
    }
    item_count
    total_price
  }
}
 ```
#### HTTP Requests

```
{
  "mutation": "{add_to_cart(purchasing_products: {item_id: 1, count: 4}, cart_id: 2) { cart_id customer_id items { item_id title} item_count } }"
}
 ```


