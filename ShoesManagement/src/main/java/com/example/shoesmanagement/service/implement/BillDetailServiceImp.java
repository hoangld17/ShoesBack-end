package com.example.shoesmanagement.service.implement;

import com.example.shoesmanagement.dto.response.BillDetailResponse;
import com.example.shoesmanagement.exception.ApplicationException;
import com.example.shoesmanagement.model.Bill;
import com.example.shoesmanagement.model.BillDetail;
import com.example.shoesmanagement.model.ShoeDetail;
import com.example.shoesmanagement.model.enums.TypeBill;
import com.example.shoesmanagement.repository.BillDetailRepository;
import com.example.shoesmanagement.repository.BillRepository;
import com.example.shoesmanagement.repository.ShoesDetailRepository;
import com.example.shoesmanagement.repository.specification.BillDetailSpecification;
import com.example.shoesmanagement.repository.specification.BillSpecification;
import com.example.shoesmanagement.service.BillDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BillDetailServiceImp implements BillDetailService {

    @Autowired
    private BillDetailRepository billDetailRepository;

    @Autowired
    private ShoesDetailRepository shoesDetailRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillDetailSpecification billDetailSpecification;

    @Autowired
    private BillSpecification billSpecification;


    @Override
    public BillDetailResponse saveBillDetail(BillDetail billDetail) {
        String USERNAME = SecurityContextHolder.getContext().getAuthentication().getName();
        final Specification<Bill> specification = this.billSpecification.doFilterBillDetailByCart(USERNAME);
        final List<Bill> bills = billRepository.findAll(specification);

        if (bills.isEmpty()) {
            final Bill newBill = new Bill();
            newBill.setBillType(TypeBill.Export);
            newBill.setCart(true);
            newBill.setTotal(0);
            newBill.setConsumerUsername(USERNAME);
            newBill.setCreatedBy(USERNAME);
            newBill.setCreatedDate(new Date());
            newBill.setUpdatedDate(new Date());
            bills.add(billRepository.save(newBill));
        }
        billDetail.setIdBill(bills.get(0).getId());
        billDetailRepository.save(billDetail);
        final BillDetailResponse response = new BillDetailResponse();
        response.setShoeDetail(getShoeDetail(billDetail.getIdShoeDetail()));
        response.setQuantity(billDetail.getQuantity());
        response.setId(billDetail.getId());
        response.setIdBill(bills.get(0).getId());
        response.setUsername(USERNAME);
        return response;
    }

    @Override
    public List<BillDetailResponse> getAllBillDetailInCart() {
        String USERNAME = SecurityContextHolder.getContext().getAuthentication().getName();
        final Specification<Bill> billSpecification = this.billSpecification.doFilterBillDetailByCart(USERNAME);
        final List<Bill> bills = billRepository.findAll(billSpecification);
        if (bills.isEmpty())
            return new ArrayList<>();
        final List<BillDetail> billDetails = billDetailRepository.findAllByIdBill(bills.get(0).getId());
        return billDetails.stream()
                .map(billDetail -> {
                    BillDetailResponse response = new BillDetailResponse();
                    response.setUsername(USERNAME);
                    response.setId(billDetail.getId());
                    response.setQuantity(billDetail.getQuantity());
                    response.setIdBill(billDetail.getIdBill());
                    response.setShoeDetail(getShoeDetail(billDetail.getIdShoeDetail()));
                    return response;
                })
                .collect(Collectors.toList());
    }

    private ShoeDetail getShoeDetail(Long id) {
        final ShoeDetail oneById = shoesDetailRepository.findOneById(id);
        //Redundant if work with font end
        if (oneById == null)
            throw new ApplicationException(HttpStatus.NOT_FOUND, "The shoe not found");
        return oneById;
    }


}
