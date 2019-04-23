package com.wpate.myvent.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Data
public class CategoryDTO {

    private long id;
    @NotEmpty
    @Length(min = 3, max = 50)
    private String name;
    private String description;

    public static CategoryDTO categoryDtoOf(Category category){
        return new CategoryDTO(category.getId(), category.getName(), category.getDescription());
    }

    public static Category categoryOf(CategoryDTO categoryDTO){
        return new Category(categoryDTO.id, categoryDTO.name, categoryDTO.description);
    }
}
