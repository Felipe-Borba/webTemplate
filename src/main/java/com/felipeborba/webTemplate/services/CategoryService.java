package com.felipeborba.webTemplate.services;

import com.felipeborba.webTemplate.dto.CategoryDTO;
import com.felipeborba.webTemplate.entities.Category;
import com.felipeborba.webTemplate.repositories.CategoryRepository;
import com.felipeborba.webTemplate.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> list = this.categoryRepository.findAll();

        return list.stream().map(CategoryDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity no found"));
        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category = this.categoryRepository.save(category);
        return new CategoryDTO(category);
    }
}
