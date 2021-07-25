package com.example.shoesmanagement.service.implement;

import com.example.shoesmanagement.exception.ApplicationException;
import com.example.shoesmanagement.model.Shoe;
import com.example.shoesmanagement.model.enums.AppStatus;
import com.example.shoesmanagement.model.util.ModelConstant;
import com.example.shoesmanagement.model.util.Validator;
import com.example.shoesmanagement.repository.ShoesRepository;
import com.example.shoesmanagement.service.BrandService;
import com.example.shoesmanagement.service.ShoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class ShoesServiceImp implements ShoesService {
    @Autowired
    private ShoesRepository shoeRepository;
    @Autowired
    private BrandService brandService;

    @Override
    public Shoe saveShoe(Shoe shoe) {
        if (brandService.getBrandById(shoe.getIdBrand()) == null)
            throw new ApplicationException("Brand is not exist!", HttpStatus.NOT_FOUND);
        return shoeRepository.save(shoe);
    }

    @Override
    public List<Shoe> getShoeByIdBrand(Long id) {
        return shoeRepository.findAllByIdBrandAndStatus(id, AppStatus.ACTIVE);
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
