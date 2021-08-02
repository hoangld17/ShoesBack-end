package com.example.shoesmanagement.dto.response;


import com.example.shoesmanagement.model.ShoeDetail;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BillDetailResponse {
    private Long id;
    private Long idBill;
    private String username;
    private ShoeDetail shoeDetail;
    private int quantity;
}
