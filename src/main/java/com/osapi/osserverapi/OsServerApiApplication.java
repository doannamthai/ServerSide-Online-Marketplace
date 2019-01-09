package com.osapi.osserverapi;

import com.osapi.osserverapi.entity.Product;
import com.osapi.osserverapi.entity.ShoppingCart;
import com.osapi.osserverapi.repository.ProductRepository;

import com.osapi.osserverapi.repository.ShoppingCartRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;


@SpringBootApplication
public class OsServerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OsServerApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(ProductRepository productRepository, ShoppingCartRepository shoppingCartRepository) {
		return (args) -> {
			for (int i = 0; i <= 10; i++){
				BigDecimal price = new BigDecimal(i*100+50);
				long count = i+3;
				long id = i+1;
				Product product = new Product(id, "Thai Doan", price, count);
				productRepository.save(product);
			}
			ShoppingCart shoppingCart = new ShoppingCart();
			Long id = Long.valueOf(1);
			shoppingCart.setCart_id(id);
			shoppingCartRepository.save(shoppingCart);
		};
	}

}

