package com.example.shoesmanagement.service.implement;

import com.example.shoesmanagement.dto.response.ShoeDetailResponse;
import com.example.shoesmanagement.dto.response.ShoeResponse;
import com.example.shoesmanagement.exception.ApplicationException;
import com.example.shoesmanagement.model.Shoe;
import com.example.shoesmanagement.model.ShoeDetail;
import com.example.shoesmanagement.model.enums.AppStatus;
import com.example.shoesmanagement.model.util.ModelConstant;
import com.example.shoesmanagement.model.util.Validator;
import com.example.shoesmanagement.repository.ShoesDetailRepository;
import com.example.shoesmanagement.repository.ShoesRepository;
import com.example.shoesmanagement.service.BrandService;
import com.example.shoesmanagement.service.ShoesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ShoesServiceImp implements ShoesService {
    @Autowired
    private ShoesRepository shoeRepository;
    @Autowired
    private ShoesDetailRepository shoesDetailRepository;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ShoeResponse saveShoe(Shoe shoe, List<ShoeDetail> shoeDetails) {
        brandService.getBrandById(shoe.getIdBrand());
        Shoe newShoe = shoeRepository.save(shoe);
        shoeDetails.forEach(x -> x.setIdShoe(newShoe.getId()));
        shoeDetails = shoeDetails.stream().map(x -> shoesDetailRepository.save(x)).collect(Collectors.toList());
        return merge(shoe, shoeDetails);
    }

    @Override
    public List<ShoeResponse> getShoeByIdBrand(Long id) {
        brandService.getBrandById(id);
        List<Shoe> shoes = shoeRepository.findAllByIdBrand(id);
        return shoes.stream().map(x -> merge(x)).collect(Collectors.toList());
    }

    @Override
    public ShoeResponse getShoeById(Long id) {
        return merge(shoeRepository.findOneById(id));
    }

    @Override
    public List<ShoeResponse> getAllShoe() {
        return ((List<Shoe>) shoeRepository.findAll()).stream().map(x -> merge(x)).collect(Collectors.toList());
    }

    private ShoeResponse merge(Shoe shoe, List<ShoeDetail> shoeDetails){
        ShoeResponse shoeResponse = modelMapper.map(shoe, ShoeResponse.class);
        List<ShoeDetailResponse> shoeDetailResponses = shoeDetails.stream().map(x -> modelMapper.map(x, ShoeDetailResponse.class)).collect(Collectors.toList());
        shoeResponse.setNameBrand(brandService.getBrandById(shoe.getIdBrand()).getName());
        shoeResponse.setShoeDetailResponses(shoeDetailResponses);
        return shoeResponse;
    }
    private ShoeResponse merge(Shoe shoe){
        ShoeResponse shoeResponse = modelMapper.map(shoe, ShoeResponse.class);
        List<ShoeDetailResponse> shoeDetailResponses = shoesDetailRepository.findAllByIdShoe(shoe.getId()).stream().map(x -> modelMapper.map(x, ShoeDetailResponse.class)).collect(Collectors.toList());
        shoeResponse.setNameBrand(brandService.getBrandById(shoe.getIdBrand()).getName());
        shoeResponse.setShoeDetailResponses(shoeDetailResponses);
        return shoeResponse;
    }
}
