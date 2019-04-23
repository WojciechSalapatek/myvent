package com.wpate.myvent.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories/{id}")
    public CategoryDTO getCategory(@PathVariable long id){
        var category = categoryService.getCategory(id);
        return CategoryDTO.categoryDtoOf(category);
    }

    @GetMapping("/categories")
    public List<CategoryDTO> getCategories(){
        var categories = categoryService.getAll();
        return categories.stream()
                .map(c -> CategoryDTO.categoryDtoOf(c))
                .collect(Collectors.toList());
    }

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        var category = CategoryDTO.categoryOf(categoryDTO);
        categoryService.add(category);
    }
}
