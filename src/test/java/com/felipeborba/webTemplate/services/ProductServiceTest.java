package com.felipeborba.webTemplate.services;

import com.felipeborba.webTemplate.repositories.ProductRepository;
import com.felipeborba.webTemplate.services.exceptions.DatabaseException;
import com.felipeborba.webTemplate.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository productRepository;

    private long existingId;
    private long noExistingId;
    private long dependentId;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        noExistingId = 1000L;
        dependentId = 4L;

        Mockito.doNothing().when(productRepository).deleteById(existingId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(noExistingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependentId);
    }

    @Test
    public void deleteShouldDataIntegrityViolationExceptionWhenIdIsDependent() {

        assertThrows(DatabaseException.class, () -> {
            service.delete(dependentId);
        });
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(dependentId);
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {

        assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(noExistingId);
        });
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(noExistingId);
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {

        assertDoesNotThrow(() -> {
            service.delete(existingId);
        });
        Mockito.verify(productRepository, Mockito.times(1)).deleteById(existingId);
    }
}
