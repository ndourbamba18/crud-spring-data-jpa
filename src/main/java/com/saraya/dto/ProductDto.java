package com.saraya.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductDto {
    @NotNull
    @Size(max = 100)
    private String productName;
    @NotNull
    private String productVendor;
    @NotNull
    @Min(0)
    private double productPrice;
    private boolean productIsInStock;

    public ProductDto() {}

    public ProductDto(String productName, String productVendor, double productPrice, boolean productIsInStock) {
        this.productName = productName;
        this.productVendor = productVendor;
        this.productPrice = productPrice;
        this.productIsInStock = productIsInStock;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductVendor() {
        return productVendor;
    }

    public void setProductVendor(String productVendor) {
        this.productVendor = productVendor;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public boolean isProductIsInStock() {
        return productIsInStock;
    }

    public void setProductIsInStock(boolean productIsInStock) {
        this.productIsInStock = productIsInStock;
    }
}
