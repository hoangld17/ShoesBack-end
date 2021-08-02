package com.example.shoesmanagement.dto.request;

import com.example.shoesmanagement.model.enums.TypeBill;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateBillRequest {
    private String purchaseDate;
    private double total;
}
