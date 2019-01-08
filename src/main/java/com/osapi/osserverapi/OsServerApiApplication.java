package com.osapi.osserverapi;

import com.osapi.osserverapi.entity.Product;
import com.osapi.osserverapi.repository.ProductRepository;
import com.osapi.osserverapi.service.ProductService;

import com.osapi.osserverapi.service.Query;
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
	public CommandLineRunner demo(ProductRepository productRepository) {
		return (args) -> {
			for (int i = 0; i <= 10; i++){
				BigDecimal price = new BigDecimal(i*100+50);
				long count = i+3;
				long id = i+1;
				Product product = new Product(id, "Thai Doan", price, count);
				productRepository.save(product);
			}
		};
	}

}

