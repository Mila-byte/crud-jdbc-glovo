package com.glovoupgraded.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("product")
@Data
@AllArgsConstructor
@Builder
public class ProductEntity {
    @Id
    private int id;
    private String name;
    private float cost;
}
