package com.example.shoesmanagement.service;

import com.example.shoesmanagement.model.Shoe;

import java.util.Collection;

public interface ShoesService {
    void saveShoe(Shoe shoe);
Shoe getShoeById(Long id);
    Collection<Shoe> getAllShoe();

}
