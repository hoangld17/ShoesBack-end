package com.example.shoesmanagement.controller;

import com.example.shoesmanagement.controller.helper.MappingHelper;
import com.example.shoesmanagement.dto.request.BillDetailRequest;
import com.example.shoesmanagement.dto.response.ShowDataResponse;
import com.example.shoesmanagement.model.BillDetail;
import com.example.shoesmanagement.service.BillDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/billDetail")
public class BillDetailController {
    @Autowired
    private BillDetailService billDetailService;

    @PostMapping
    public ShowDataResponse<?> addBillDetail(@RequestBody BillDetailRequest request) {
        final BillDetail billDetail = MappingHelper.mapBillDetail(request);
        return new ShowDataResponse<>(billDetailService.saveBillDetail(billDetail));
    }
//Redundant
    @GetMapping()
    public ShowDataResponse<?> getAllBillDetailInCart() {
        return new ShowDataResponse<>(billDetailService.getAllBillDetailInCart());
    }
}
