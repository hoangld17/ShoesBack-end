package com.example.shoesmanagement.dto.request;


import com.example.shoesmanagement.model.enums.Color;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateShoeRequest {
    private String name;
    private double price;
    private Long idBrand;
    private List<Color> colors;
    private List<Double> sizes;
}
