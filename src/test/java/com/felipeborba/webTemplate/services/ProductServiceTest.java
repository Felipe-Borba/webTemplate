package com.felipeborba.webTemplate.services;

import com.felipeborba.webTemplate.Factory;
import com.felipeborba.webTemplate.dto.ProductDTO;
import com.felipeborba.webTemplate.entities.Product;
import com.felipeborba.webTemplate.repositories.ProductRepository;
import com.felipeborba.webTemplate.services.exceptions.DatabaseException;
import com.felipeborba.webTemplate.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository productRepository;

    private long existingId;
    private long noExistingId;
    private long dependentId;
    private PageImpl<Product> page;
    private Product product;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        noExistingId = 1000L;
        dependentId = 4L;
        product = Factory.createProduct();
        page = new PageImpl<>(List.of(product));

        Mockito.when(productRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
        Mockito.when(productRepository.save(ArgumentMatchers.any())).thenReturn(product);
        Mockito.when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.findById(noExistingId)).thenReturn(Optional.empty());

        Mockito.doNothing().when(productRepository).deleteById(existingId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(noExistingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependentId);
    }

    @Test
    public void findAllPagedShouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<ProductDTO> result = service.findAllPaged(pageable);

        Assertions.assertNotNull(result);
        Mockito.verify(productRepository, Mockito.times(1)).findAll(pageable);
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
