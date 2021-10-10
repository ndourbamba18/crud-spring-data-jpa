package com.saraya;

import com.saraya.entities.Product;
import com.saraya.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Tp1RestApisSpringBootApplication {

	public static void main(String[] args) {

		SpringApplication.run(Tp1RestApisSpringBootApplication.class, args);
	}

	
	@Bean
	CommandLineRunner commandLineRunner(ProductRepository productRepository){
		return args -> {
			productRepository.save(new Product("Lenovo Pc", "", 105.00, true));
			productRepository.save(new Product("Mac Pro Pc", "", 250.00, false));
			productRepository.save(new Product("Iphone 7", "", 140.00, true));
			productRepository.save(new Product("Acer Pc", "", 150.00, true));
			productRepository.save(new Product("Samsung Pc", "", 85.78, false));
			productRepository.save(new Product("Hp Pc", "", 100.00, true));
			productRepository.save(new Product("Car", "", 700.15, true));
			productRepository.save(new Product("Cables Iphone 11", "", 25.50, false));
			productRepository.save(new Product("Dell Pc", "", 100.50, true));
			productRepository.save(new Product("Techno Spark 7", "", 78.00, true));
			productRepository.findAll().forEach(product -> {System.out.println(product.getProductName());

			});
		};
	}

}
