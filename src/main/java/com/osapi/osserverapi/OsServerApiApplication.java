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

