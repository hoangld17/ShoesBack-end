package com.example.shoesmanagement.service;

import com.example.shoesmanagement.dto.response.BillResponse;
import com.example.shoesmanagement.model.Bill;

import java.util.List;

public interface BillService {
    BillResponse addBill(Bill bill);

    List<BillResponse> getAllPayedBills();

    List<BillResponse> getAllBillsByUsername(String username);

    BillResponse getCart();

    BillResponse getBillById(Long id);

    BillResponse getBillByIdAdmin(Long id);


    Bill saveBill(Bill bill);
}
