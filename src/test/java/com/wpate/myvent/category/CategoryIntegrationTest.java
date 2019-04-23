package com.wpate.myvent.category;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CategoryIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private CategoryRepository repository;

    @Test
    public void getCategory_ReturnsCategory(){
        //arrange
        given(repository.findById(anyLong())).willReturn(java.util.Optional.of(new Category(0, "Sport", "dsc")));

        //act
        ResponseEntity<CategoryDTO> response = restTemplate.getForEntity("/categories/0", CategoryDTO.class);

        //assert
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isEqualTo(0);
        Assertions.assertThat(response.getBody().getName()).isEqualTo("Sport");
        Assertions.assertThat(response.getBody().getDescription()).isEqualTo("dsc");
    }

    @Test
    public void getCategory_NotFoundReturns404(){
        //arrange
        given(repository.findById(anyLong())).willReturn(java.util.Optional.empty());
        //act
        ResponseEntity<Category> response = restTemplate.getForEntity("/categories/0", Category.class);

        //assert
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
