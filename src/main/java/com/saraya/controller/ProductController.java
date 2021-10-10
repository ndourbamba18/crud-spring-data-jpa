package com.saraya.controller;

import com.saraya.dto.ProductDto;
import com.saraya.entities.Product;
import com.saraya.message.Message;
import com.saraya.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /*
     * GETTING ALL PRODUCTS
     * URL : http://127.0.0.1:8080/products/all
     */
    @GetMapping(path = "/products/all")
    public ResponseEntity<?> getAllProducts(){
       try {
           List<Product> products = productRepository.findAll();
           if (products.isEmpty())
               return new ResponseEntity<>(new Message("List of product is empty!"), HttpStatus.BAD_GATEWAY);
           return new ResponseEntity<>(products, HttpStatus.OK);
       }catch (Exception e){
           return new ResponseEntity<>(new Message("ERROR REQUEST!"), HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    /*
     * GETTING A SINGLE PRODUCT BY ID
     * URL : http://127.0.0.1:8080/products/detail/{id}
     */
    @GetMapping(path = "/products/detail/{productId}")
    public ResponseEntity<?> getProductDetailById(@PathVariable("productId") Long productId){
        try {
            if(!productRepository.existsById(productId))
                return new ResponseEntity<>(new Message("Product does not exist : "+productId+"!"), HttpStatus.NOT_FOUND);
            Product product = productRepository.findById(productId).get();
            return new ResponseEntity<>(product, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new Message("ERROR REQUEST"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * GETTING A SINGLE PRODUCT BY NAME
     * URL : http://127.0.0.1:8080/products/detail-name/{productName}
     */
    @GetMapping(path = "/products/detail-name/{productName}")
    public ResponseEntity<?> getProductDetailByName(@PathVariable("productName") String productName){
        try {
            if(!productRepository.existsByProductName(productName))
                return new ResponseEntity<>(new Message("Product does not exist : "+productName+"!"), HttpStatus.NOT_FOUND);
            Product product = productRepository.findByProductNameContains(productName).get();
            return new ResponseEntity<>(product, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new Message("ERROR REQUEST!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /*
     * SEARCH ALL PRODUCTS FROM DATABASE
     * URL : http://127.0.0.1:8080/products/search/{keyword}
     */
    @GetMapping(path = "/products/search/{keyword}")
    public ResponseEntity<?> searchProductsByKeyword(@PathVariable("keyword") String keyword){
    	try {
    		List<Product> products = productRepository.findByKeyword(keyword);
       	 if (products.isEmpty())
                return new ResponseEntity<>(new Message("Sorry, but we haven't heard back!"), HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(products, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new Message("ERROR REQUEST!"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    /*
     * POST A NEW PRODUCT FROM DATABASE
     * URL : http://127.0.0.1:8080/products/new
     */
    @PostMapping(path = "/products/new")
    public ResponseEntity<?> saveNewProduct(@Valid @RequestBody ProductDto productDto){
       try {
           if(productRepository.existsByProductName(productDto.getProductName()))
               return new ResponseEntity<>(new Message("Product name already exist!"), HttpStatus.BAD_REQUEST);
           if(productDto.getProductPrice()==0.0 || productDto.getProductPrice()<0.0)
               return new ResponseEntity<>(new Message("The price must be greater than 0!"), HttpStatus.BAD_REQUEST);
           if(productDto.getProductName()=="" || productDto.getProductVendor()=="")
        	   return new ResponseEntity<>(new Message("This field is required!"), HttpStatus.BAD_REQUEST);

           Product product = new Product(productDto.getProductName(), productDto.getProductVendor(),
                   productDto.getProductPrice(), productDto.isProductIsInStock());
           productRepository.save(product);
           return new ResponseEntity<>(new Message("Product has been created successfully : "+product.getProductName()+"!"), HttpStatus.CREATED);
       }catch (Exception e){
           return new ResponseEntity<>(new Message("ERROR REQUEST!"), HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    /*
     * DELETE A PRODUCT BY ID FROM DATABASE
     * URL : http://127.0.0.1:8080/products/delete/{id}
     */
    @DeleteMapping(path = "/products/delete/{productId}")
    public ResponseEntity<?> deleteProductById(@PathVariable("productId") Long productId){
       try {
           if(!productRepository.existsById(productId))
               return new ResponseEntity<>(new Message("Product does not exist : "+productId), HttpStatus.NOT_FOUND);
           Product product = productRepository.findById(productId).get();
           productRepository.delete(product);
           return new ResponseEntity<>(new Message("Product has been deleted successfully : "+productId+"!"), HttpStatus.OK);
       }catch (Exception e){
           return new ResponseEntity<>(new Message("ERROR REQUEST"), HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    /*
     * UPDATE A PRODUCT BY ID FROM DATABASE
     * URL : http://127.0.0.1:8080/products/update/{id}
     */
    @PutMapping(path = "/products/update/{productId}")
    public ResponseEntity<?> updateProductById(@PathVariable("productId") Long productId, @Valid @RequestBody ProductDto productDto){
       try {
           if(!productRepository.existsById(productId))
               return new ResponseEntity<>(new Message("Product does not exist : "+productId), HttpStatus.NOT_FOUND);
           if (productRepository.existsByProductName(productDto.getProductName()) &&
                   productRepository.findByProductNameContains(productDto.getProductName()).get().getProductId() != productId)
               return new ResponseEntity<>(new Message("Product name already exist!"), HttpStatus.BAD_REQUEST);
           if(productDto.getProductName()=="" || productDto.getProductVendor()=="")
        	   return new ResponseEntity<>(new Message("This field is required!"), HttpStatus.BAD_REQUEST);

           Product product = productRepository.findById(productId).get();
           product.setProductName(productDto.getProductName());
           product.setProductPrice(productDto.getProductPrice());
           product.setProductVendor(productDto.getProductVendor());
           product.setProductIsInStock(productDto.isProductIsInStock());
           productRepository.save(product);
           return new ResponseEntity<>(new Message("Product has been updated successfully : "+productId+"!"), HttpStatus.OK);
       }catch (Exception e){
           return new ResponseEntity<>(new Message("ERROR REQUEST"), HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }
}
