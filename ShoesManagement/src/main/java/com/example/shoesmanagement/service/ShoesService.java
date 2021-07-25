package com.example.shoesmanagement.service;

import com.example.shoesmanagement.model.Shoe;

import java.util.Collection;
import java.util.List;

public interface ShoesService {
    Shoe saveShoe(Shoe shoe);

    List<Shoe> getShoeByIdBrand(Long id);

    Shoe getShoeById(Long id);

    Collection<Shoe> getAllShoe();

}
