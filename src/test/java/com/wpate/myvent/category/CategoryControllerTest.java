package com.wpate.myvent.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.wpate.myvent.category.exceptions.CategoryNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CategoryService categoryService;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getCategory_ReturnsCategoryDetails() throws Exception {
        //arrange
        given(categoryService.getCategory(anyLong())).willReturn(new Category(0, "Sport", "dsc"));

        //act

        //assert
        mvc.perform(MockMvcRequestBuilders.get("/categories/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(0))
                .andExpect(jsonPath("name").value("Sport"))
                .andExpect(jsonPath("description").value("dsc"));
    }

    @Test
    public void getCategory_NotFoundThrowsException() throws Exception {
        //arrange
        given(categoryService.getCategory(anyLong())).willThrow(new CategoryNotFoundException("Category with given id not found"));

        //act

        //assert
        mvc.perform(MockMvcRequestBuilders.get("/categories/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void postCategory_NameTooLong_ShouldReturnValidationErrors() throws Exception {
        //arrange
        String title = Strings.repeat("x", 51);
        var categoryDto = new  CategoryDTO(0, title, "");

        //assert
        mvc.perform(MockMvcRequestBuilders.post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(categoryDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors[*].field", contains("name")))
                .andExpect(jsonPath("$.fieldErrors[*].message", containsInAnyOrder(
                        "length must be between 3 and 50"
                )));
    }

}
