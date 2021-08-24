package com.example.shoesmanagement.controller.helper;

import com.example.shoesmanagement.admindto.request.AdminBillDetailRequest;
import com.example.shoesmanagement.admindto.request.AdminShoeCreateRequest;
import com.example.shoesmanagement.admindto.request.AdminShoeUpdateRequest;
import com.example.shoesmanagement.dto.request.CreateConsumerRequest;
import com.example.shoesmanagement.dto.request.CreateShoeRequest;
import com.example.shoesmanagement.dto.request.UpdateConsumerRequest;
import com.example.shoesmanagement.model.BillDetail;
import com.example.shoesmanagement.model.Consumer;
import com.example.shoesmanagement.model.Shoe;
import com.example.shoesmanagement.model.ShoeDetail;
import com.example.shoesmanagement.model.enums.AppStatus;
import com.example.shoesmanagement.model.enums.UserRole;
import com.example.shoesmanagement.model.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MappingHelper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static Consumer mapConsumer(Consumer consumer, UpdateConsumerRequest updateConsumerRequest) {
        consumer.setFirstName(updateConsumerRequest.getFirstName());
        consumer.setLastName(updateConsumerRequest.getLastName());
        consumer.setEmail(updateConsumerRequest.getEmail());
        consumer.setPhone(updateConsumerRequest.getPhone());
        consumer.setAddress(updateConsumerRequest.getAddress());
        return consumer;
    }

    public static BillDetail mapAdminBillDetail(AdminBillDetailRequest request) {
        final BillDetail billDetail = new BillDetail();
        billDetail.setIdShoeDetail(request.getIdShoeDetail());
        billDetail.setQuantity(request.getQuantity());
        return billDetail;
    }

    public static Shoe mapShoe(CreateShoeRequest createShoeRequest) {
        Shoe shoe = new Shoe();
        shoe.setIdBrand(createShoeRequest.getIdBrand());
        shoe.setName(createShoeRequest.getName());
        shoe.setPrice(createShoeRequest.getPrice());
        shoe.setDescription(createShoeRequest.getDescription());
        shoe.setDiscount(createShoeRequest.getDiscount());
        shoe.setTotalImages(createShoeRequest.getImages().size());
        shoe.setStatus(AppStatus.ACTIVE);
        return shoe;
    }

    public static List<ShoeDetail> mapShoeDetail(CreateShoeRequest createShoeRequest) {
        List<ShoeDetail> list = new ArrayList<>();
        for (double size : createShoeRequest.getSizes()) {
            ShoeDetail shoeDetail = new ShoeDetail();
            shoeDetail.setCurrentQuantity(0);
            shoeDetail.setSize(size);
            list.add(shoeDetail);
        }
        return list;
    }

    public static void mapShoeUpdate(AdminShoeUpdateRequest request, Shoe shoe) {
        shoe.setPrice(request.getPrice());
        shoe.setDiscount(request.getDiscount());
        shoe.setName(request.getName());
        shoe.setDescription(request.getDescription());
        shoe.setTotalImages(request.getImages().size());
    }

    public static Shoe mapShoeCreate(AdminShoeCreateRequest request) {
        final Shoe shoe = new Shoe();
        shoe.setDescription(request.getDescription());
        shoe.setDiscount(request.getDiscount());
        shoe.setName(request.getName());
        shoe.setIdBrand(request.getIdBrand());
        shoe.setPrice(request.getPrice());
        shoe.setTotalImages(request.getImages().size());
        return shoe;
    }
    public Consumer mapConsumer(CreateConsumerRequest createConsumerRequest) throws IOException {
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
        consumer.setImage(createConsumerRequest.getImage());
        return consumer;
    }
}
