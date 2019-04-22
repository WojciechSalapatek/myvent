package com.wpate.myvent.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    private long id;
    @Column(nullable = false)
    private String name;
    private String description;

}
