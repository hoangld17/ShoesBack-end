package com.example.shoesmanagement.service;

import com.example.shoesmanagement.dto.response.ShoeResponse;
import com.example.shoesmanagement.model.Shoe;
import com.example.shoesmanagement.model.ShoeDetail;

import java.util.Collection;
import java.util.List;

public interface ShoesService {
    ShoeResponse saveShoe(Shoe shoe, List<ShoeDetail> shoeDetails);

    List<ShoeResponse> getShoeByIdBrand(Long id);

    ShoeResponse getShoeById(Long id);

    List<ShoeResponse> getAllShoe();



}
