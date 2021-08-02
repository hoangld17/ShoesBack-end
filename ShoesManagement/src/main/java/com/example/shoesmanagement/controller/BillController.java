package com.example.shoesmanagement.controller;

import com.example.shoesmanagement.controller.helper.MappingHelper;
import com.example.shoesmanagement.dto.request.UpdateBillRequest;
import com.example.shoesmanagement.dto.response.BillResponse;
import com.example.shoesmanagement.dto.response.ShowDataResponse;
import com.example.shoesmanagement.exception.ApplicationException;
import com.example.shoesmanagement.model.Bill;
import com.example.shoesmanagement.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bill")
public class BillController {
    @Autowired
    BillService billService;

    @PutMapping()
    public ShowDataResponse<?> paymentLoginUser(@RequestBody UpdateBillRequest request) {
        final BillResponse cart = billService.getCart();
        final Bill bill = MappingHelper.mapBillAuthentication(request,cart);
        return new ShowDataResponse<>(billService.saveBill(bill));
    }
    @GetMapping()
    public ShowDataResponse<?> getBillById(@RequestParam Long id) {
        return new ShowDataResponse<>(billService.getBillById(id));
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public ShowDataResponse<?> getBillByIdRoleAdmin(@RequestParam Long id) {
        return new ShowDataResponse<>(billService.getBillByIdAdmin(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "/username")
    public ShowDataResponse<?> getAllByUsername(@RequestParam String username) {
        return new ShowDataResponse<>(billService.getAllBillsByUsername(username));
    }


    @GetMapping(path = "/history")
    public ShowDataResponse<?> getHistoryShopping() {
        return new ShowDataResponse<>(billService.getAllPayedBills());
    }

    @GetMapping(path = "/cart")
    public ShowDataResponse<?> getCart() {
        return new ShowDataResponse<>(billService.getCart());
    }
}
