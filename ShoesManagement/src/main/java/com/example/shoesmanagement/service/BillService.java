package com.example.shoesmanagement.service;

import com.example.shoesmanagement.dto.response.OneBillUserResponse;
import com.example.shoesmanagement.model.Bill;
import com.example.shoesmanagement.model.Consumer;

public interface BillService {
    OneBillUserResponse getCart();

    Bill createBillEmptyUser(Consumer consumer);
}
