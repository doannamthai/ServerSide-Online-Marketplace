package com.osapi.osserverapi;

import com.osapi.osserverapi.entity.Product;
import com.osapi.osserverapi.entity.ShoppingCart;
import com.osapi.osserverapi.exception.GraphQLErrorAdapter;
import com.osapi.osserverapi.repository.ProductRepository;

import com.osapi.osserverapi.repository.ShoppingCartRepository;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GraphQLErrorHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootApplication
public class OsServerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OsServerApiApplication.class, args);
	}

	/**
	 * Test products
	 * @param productRepository
	 * @param shoppingCartRepository
	 * @return new runner
	 */
	@Bean
	public CommandLineRunner demo(ProductRepository productRepository, ShoppingCartRepository shoppingCartRepository) {
		return (args) -> {
			long id = 0;
			Product product1 = new Product(id + 1, "iPhone 11S", new BigDecimal(1500), (long) 10);
			Product product2 = new Product(id + 2, "Dell XPS 15", new BigDecimal(1200), (long) 20);
			Product product3 = new Product(id + 3, "Macbook Pro", new BigDecimal(1800), (long) 12);
			Product product4 = new Product(id + 4, "Samsung Galaxy S7", new BigDecimal(800), (long) 0);
			Product product5 = new Product(id + 5, "Bitcoin", new BigDecimal(4000), (long) 500);
			Product product6 = new Product(id + 6, "Hello World!", new BigDecimal(50), (long) 1000);
			productRepository.save(product1);
			productRepository.save(product2);
			productRepository.save(product3);
			productRepository.save(product4);
			productRepository.save(product5);
			productRepository.save(product6);
		};
	}

	/**
	 * Customize error handler
	 * @return new error handler
	 */
	@Bean
	public GraphQLErrorHandler errorHandler() {
		return new GraphQLErrorHandler() {
			@Override
			public List<GraphQLError> processErrors(List<GraphQLError> errors) {
				List<GraphQLError> clientErrors = errors.stream()
						.filter(this::isClientError)
						.collect(Collectors.toList());

				List<GraphQLError> serverErrors = errors.stream()
						.filter(e -> !isClientError(e))
						.map(GraphQLErrorAdapter::new)
						.collect(Collectors.toList());

				List<GraphQLError> e = new ArrayList<>();
				e.addAll(clientErrors);
				e.addAll(serverErrors);
				return e;
			}

			protected boolean isClientError(GraphQLError error) {
				return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
			}
		};
	}

}

