package com.saraya.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(
        name = "products",
        uniqueConstraints = {@UniqueConstraint(columnNames = "product_name")}
)
public class Product extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long productId;
    @NotNull
    @Size(max = 100)
    @Column(name = "product_name")
    private String productName;
    @NotNull
    @Column(name = "product_vendor")
    private String productVendor;
    @NotNull
    @Min(0)
    @Column(name = "product_price")
    private double productPrice;
    @Column(name = "product_is_in_stock")
    private boolean productIsInStock;

    public Product() {}

    public Product(String productName, String productVendor, double productPrice, boolean productIsInStock) {
        this.productName = productName;
        this.productVendor = productVendor;
        this.productPrice = productPrice;
        this.productIsInStock = productIsInStock;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productVendor='" + productVendor + '\'' +
                ", productPrice=" + productPrice +
                ", productIsInStock=" + productIsInStock +
                '}';
    }
}
