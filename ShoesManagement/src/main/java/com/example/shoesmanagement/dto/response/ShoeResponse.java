package com.example.shoesmanagement.dto.response;

import com.example.shoesmanagement.model.enums.AppStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ShoeResponse {
    private Long id;
    private String nameBrand;
    private String name;
    private double price;
    private AppStatus status;
    private List<ShoeDetailResponse> shoeDetailResponses;
}
