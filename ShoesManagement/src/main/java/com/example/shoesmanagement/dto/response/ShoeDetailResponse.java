package com.example.shoesmanagement.dto.response;

import com.example.shoesmanagement.model.enums.Color;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShoeDetailResponse {
    private Long id;
    private double size;
    private Color color;
    private int currentQuantity;
}
