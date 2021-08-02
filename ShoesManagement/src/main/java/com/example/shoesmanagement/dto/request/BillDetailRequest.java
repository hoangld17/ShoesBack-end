package com.example.shoesmanagement.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BillDetailRequest {
    private Long idShoeDetail;
    private int quantity;
}
