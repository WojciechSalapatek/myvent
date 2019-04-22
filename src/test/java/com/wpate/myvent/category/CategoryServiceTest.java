package com.wpate.myvent.category;

import com.wpate.myvent.category.exceptions.CategoryNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    private CategoryService service;

    @Before
    public void setUp(){
        service = new CategoryService(repository);
    }

    @Test
    public void getCategory_ReturnsCategory(){
        //arrange
        given(repository.findById(0L)).willReturn(java.util.Optional.of(new Category(0, "Sport", "dsc")));

        //act
        var category = service.getCategory(0);

        //assert
        Assertions.assertThat(category.getId()).isEqualTo(0);
        Assertions.assertThat(category.getName()).isEqualTo("Sport");
        Assertions.assertThat(category.getDescription()).isEqualTo("dsc");
    }

    @Test(expected = CategoryNotFoundException.class)
    public void getCategory_NotFindThrowsException(){
        //arrange
        given(repository.findById(0L)).willReturn(java.util.Optional.empty());

        //act
        service.getCategory(0L);

    }
}

