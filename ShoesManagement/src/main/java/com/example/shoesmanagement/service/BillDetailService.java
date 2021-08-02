package com.example.shoesmanagement.service;

import com.example.shoesmanagement.dto.response.BillDetailResponse;
import com.example.shoesmanagement.model.BillDetail;

import java.util.List;

public interface BillDetailService {
    BillDetailResponse saveBillDetail(BillDetail bill);

    List<BillDetailResponse> getAllBillDetailInCart();

}
