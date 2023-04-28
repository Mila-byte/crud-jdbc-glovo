package com.glovoupgraded.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Product {
    private int id;
    private String name;
    private float cost;
}
