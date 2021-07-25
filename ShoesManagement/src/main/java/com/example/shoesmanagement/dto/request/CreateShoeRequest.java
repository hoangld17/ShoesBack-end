package com.example.shoesmanagement.dto.request;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateShoeRequest {
    private String name;
    private double price;
    private Long idBrand;
}
