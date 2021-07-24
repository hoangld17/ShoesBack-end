package com.example.shoesmanagement.service.implement;

import com.example.shoesmanagement.model.Shoe;
import com.example.shoesmanagement.repository.ShoesRepository;
import com.example.shoesmanagement.service.ShoesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class ShoesServiceImp implements ShoesService {
    private final ShoesRepository shoeRepository;

    public ShoesServiceImp(ShoesRepository shoeRepository) {
        this.shoeRepository = shoeRepository;
    }

    @Override
    public void saveShoe(Shoe shoe) {
        shoeRepository.save(shoe);
    }

    @Override
    public Shoe getShoeById(Long id) {
        return shoeRepository.findShoeById(id);
    }

    @Override
    public Collection<Shoe> getAllShoe() {
        return (Collection<Shoe>) shoeRepository.findAll();
    }


}
