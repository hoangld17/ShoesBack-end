package com.example.shoesmanagement.controller.helper;

import com.example.shoesmanagement.dto.request.*;
import com.example.shoesmanagement.dto.response.BillResponse;
import com.example.shoesmanagement.exception.ApplicationException;
import com.example.shoesmanagement.model.*;
import com.example.shoesmanagement.model.enums.AppStatus;
import com.example.shoesmanagement.model.enums.TypeBill;
import com.example.shoesmanagement.model.enums.UserRole;
import com.example.shoesmanagement.model.util.AppUtil;
import com.example.shoesmanagement.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MappingHelper {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BrandService brandService;

    public static Consumer mapConsumer(Consumer consumer, UpdateConsumerRequest updateConsumerRequest) {
        consumer.setFirstName(updateConsumerRequest.getFirstName());
        consumer.setLastName(updateConsumerRequest.getLastName());
        consumer.setEmail(updateConsumerRequest.getEmail());
        consumer.setPhone(updateConsumerRequest.getPhone());
        consumer.setAddress(updateConsumerRequest.getAddress());
        return consumer;
    }

//    public static Bill mapBill(UpdateBillRequest request) throws ParseException {
//        final Bill bill = new Bill();
//        bill.set(request.getIdConsumer());
//        bill.setBillType(TypeBill.Export);
//        bill.setAddress(request.getAddress());
//        bill.setPhone(request.getPhone());
//        bill.setPurchaseDate(request.getPurchaseDate());
//        bill.setCart(false);
//        return bill;
//
//    }

    public static Shoe mapShoe(CreateShoeRequest createShoeRequest) {
        Shoe shoe = new Shoe();
        shoe.setIdBrand(createShoeRequest.getIdBrand());
        shoe.setName(createShoeRequest.getName());
        shoe.setPrice(createShoeRequest.getPrice());
        shoe.setStatus(AppStatus.ACTIVE);
        return shoe;
    }

    public static List<ShoeDetail> mapShoeDetail(CreateShoeRequest createShoeRequest) {
        List<ShoeDetail> list = new ArrayList<>();
        for (int j = 0; j < createShoeRequest.getSizes().size(); j++) {
            ShoeDetail shoeDetail = new ShoeDetail();
            shoeDetail.setCurrentQuantity(0);
            shoeDetail.setSize(createShoeRequest.getSizes().get(j));
            list.add(shoeDetail);
        }
        return list;
    }

    public static Bill mapBillAuthentication(UpdateBillRequest request, BillResponse bill) {
        final Bill newBill = new Bill();
       newBill.setTotal(request.getTotal());
       newBill.setPurchaseDate(request.getPurchaseDate());
       newBill.setConsumerUsername(bill.getConsumerUsername());
       newBill.setBillType(bill.getBillType());
       newBill.setAddress(bill.getAddress());
       newBill.setCart(false);
       newBill.setPhone(bill.getPhone());
       newBill.setId(bill.getId());
       return newBill;
    }

    public static BillDetail mapBillDetail(BillDetailRequest request) {
        final BillDetail billDetail = new BillDetail();
        billDetail.setIdShoeDetail(request.getIdShoeDetail());
        billDetail.setQuantity(request.getQuantity());
        return billDetail;
    }



    public Consumer mapConsumer(CreateConsumerRequest createConsumerRequest) {
        Consumer consumer = new Consumer();
        consumer.setFirstName(createConsumerRequest.getFirstName());
        consumer.setLastName(createConsumerRequest.getLastName());
        consumer.setEmail(createConsumerRequest.getEmail());
        consumer.setPhone(createConsumerRequest.getPhone());
        consumer.setAddress(createConsumerRequest.getAddress());
        consumer.setUsername(createConsumerRequest.getUsername());
        String passwordSalt = AppUtil.generateSalt();
        consumer.setPasswordHash(passwordEncoder.encode(createConsumerRequest.getPassword().concat(passwordSalt)));
        consumer.setPasswordSalt(passwordSalt);
        consumer.setRole(UserRole.USER);
        consumer.setStatus(AppStatus.ACTIVE);
        consumer.setImgUrl(createConsumerRequest.getImgUrl());
        return consumer;
    }
}
