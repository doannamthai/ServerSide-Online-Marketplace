
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
 3. Or by using `HTTP Requests` to server https://serverside-os-api.herokuapp.com/graphql
 
## Entities
 *(\* indicates optional fields)*
 ### Product
 Includes 2 queries:
  - `find_products(filter: FilterList*): ItemsData` returns the filtered list of products with the count from the database
  - `find_product_by_id(item_id: ID): Product` returns the product associated with the given `item_id`
  
 ### Shopping Cart
 Includes 1 query and 6 mutations
  #### Query
  - `shopping_carts(cart_id: ID*): [ShoppingCart]` returns the shopping cart associated with the given cart_id or all the carts if `cart_id` has not been declared
  #### Mutation
  - `new_cart(customer_id: ID*): ShoppingCart` creates a new shopping cart with the given `customer_id` and returns the new`ShoppingCart`
  - `delete_cart(cart_id: ID): Boolean` deletes the shopping cart that has the given `cart_id` return true if the cart has been deleted
  
