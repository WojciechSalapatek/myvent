package com.wpate.myvent.category;

import com.wpate.myvent.category.exceptions.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private CategoryRepository repository;

    @Autowired
    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Category getCategory(long id) {
        return repository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category with this id doesn't exist"));
    }

    public void add(Category category) { repository.save(category); }
}
