package com.example.shoesmanagement.dto.response;


import com.example.shoesmanagement.model.BillDetail;
import com.example.shoesmanagement.model.enums.AppStatus;
import com.example.shoesmanagement.model.enums.TypeBill;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BillResponse {
    private Long id;
    private String consumerUsername;
    private Date purchaseDate;
    private String phone;
    private String address;
    private List<BillDetailResponse> billDetailResponses;
    private TypeBill billType;
    private double total;
    private boolean isCart;
}
