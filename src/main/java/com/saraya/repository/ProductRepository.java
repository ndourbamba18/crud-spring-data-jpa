package com.saraya.repository;

import com.saraya.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	 // CUSTOM QUERY
    @Query(value = "select * from products p where p.product_name like %:keyword% or p.product_price like %:keyword% " +
            "or p.product_vendor like %:keyword%", nativeQuery = true)
    List<Product> findByKeyword(@Param("keyword") String keyword);
	 
	 
    Optional<Product> findByProductNameContains(String productName);

    boolean existsByProductName(String productName);
}
