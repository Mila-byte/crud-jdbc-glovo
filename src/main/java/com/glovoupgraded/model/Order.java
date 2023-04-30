package com.glovoupgraded.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Order {
    private int id;
    private float cost;
    private List<Product> products;
}
