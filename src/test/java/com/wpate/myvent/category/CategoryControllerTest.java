package com.wpate.myvent.category;

import com.wpate.myvent.category.exceptions.CategoryNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CategoryService categoryService;

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

}
