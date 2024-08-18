package com.felipeborba.webTemplate;

import com.felipeborba.webTemplate.dto.ProductDTO;
import com.felipeborba.webTemplate.entities.Category;
import com.felipeborba.webTemplate.entities.Product;

import java.time.Instant;

public class Factory {
    public static Product createProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Phone");
        product.setDescription("Good Phone");
        product.setPrice(800.0);
        product.setImgUrl("https://img.com/img.png");
        product.setDate(Instant.parse("2020-07-14T10:00:00Z"));

        Category category = new Category();
        category.setId(2L);
        category.setName("Electronics");
        product.getCategories().add(category);

        return product;
    }

    public static ProductDTO createProductDTO() {
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }

}
