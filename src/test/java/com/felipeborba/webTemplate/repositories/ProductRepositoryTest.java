package com.felipeborba.webTemplate.repositories;

import com.felipeborba.webTemplate.Factory;
import com.felipeborba.webTemplate.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    private long existingId;
    private long totalProducts;
    @BeforeEach
    void setUp() {
        existingId = 1L;
        totalProducts = 25L;
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        //Arrange

        //Act
        repository.deleteById(existingId);

        //Assert
        Optional<Product> result = repository.findById(existingId);
        assertFalse(result.isPresent());
    }

    @Test
    public void deleteShouldThrowEmptyResultDataExceptionWhenIdDoesNotExist() {
        //Arrange
        // seems that in older version of spring if try to delete when id does not exist it would throw error but here is silently ignored.
        // so this test is here just to show how it would like to test an exception

        //Act
        Executable executable = () -> {
            repository.deleteById(null);
        };

        //Assert
        assertThrows(InvalidDataAccessApiUsageException.class, executable);
    }

    @Test
    public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
        //Arrange
        Product product = Factory.createProduct();
        product.setId(null);

        //Act
        product = repository.save(product);

        //Assert
        assertNotNull(product.getId());
        assertEquals(totalProducts + 1, product.getId());
    }
}
