package com.example.shoesmanagement.service.implement;

import com.example.shoesmanagement.dto.request.CreateBillDetailRequest;
import com.example.shoesmanagement.dto.response.BillDetailUserResponse;
import com.example.shoesmanagement.dto.response.BillUserResponse;
import com.example.shoesmanagement.dto.response.OneBillUserResponse;
import com.example.shoesmanagement.model.Bill;
import com.example.shoesmanagement.model.BillDetail;
import com.example.shoesmanagement.model.Consumer;
import com.example.shoesmanagement.model.enums.BillType;
import com.example.shoesmanagement.repository.BillDetailRepository;
import com.example.shoesmanagement.repository.BillRepository;
import com.example.shoesmanagement.service.BillService;
import com.example.shoesmanagement.service.BrandService;
import com.example.shoesmanagement.service.ConsumerService;
import com.example.shoesmanagement.service.ShoesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BillServiceImp implements BillService {
    @Autowired
    BillRepository billRepository;

    @Autowired
    BillDetailRepository billDetailRepository;

    @Autowired
    ConsumerService consumerService;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ShoesService shoesService;

    @Autowired
    BrandService brandService;

    @Override
    public OneBillUserResponse getCart() {
        Bill bill = getCartUser();
        OneBillUserResponse oneBillUserResponse = modelMapper.map(bill, OneBillUserResponse.class);
        List<BillDetail> billDetailList = billDetailRepository.findByIdBill(bill.getId());
        if (billDetailList.isEmpty())
            return oneBillUserResponse;
        List<BillDetailUserResponse> list = billDetailList.stream().map(x -> modelMapper.map(x, BillDetailUserResponse.class)).collect(Collectors.toList());
        shoesService.transferData(list);
        oneBillUserResponse.setListBillDetail(list);
        return oneBillUserResponse;
    }
    public Bill saveBill(Bill bill){
       return billRepository.save(bill);
    }
    public BillDetail saveBillDetail(BillDetail billDetail){
        return billDetailRepository.save(billDetail);
    }
    @Override
    public Bill createBillEmptyUser(Consumer consumer){
        Bill bill = new Bill();
        bill.setConsumerUsername(consumer.getUsername());
        bill.setBillType(BillType.Export);
        bill.setCart(true);
        bill.setAddress(consumer.getPhone());
        bill.setPhone(consumer.getPhone());
        bill.setTotal(0);
        bill.setDiscount(0);
        return saveBill(bill);
    }
    public List<BillUserResponse> listBillUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Consumer consumer = consumerService.getConsumerByUsername(username);
        return billRepository.findByIdConsumerAndBillTypeAndCart(consumer.getId(), BillType.Export, false).stream().map(x -> modelMapper.map(x, BillUserResponse.class)).collect(Collectors.toList());
    }

    public void paymentBill(){
        Bill bill = getCartUser();
        bill.setCart(false);
        bill.setPurchaseDate(new Date());
        saveBill(bill);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Consumer consumer = consumerService.getConsumerByUsername(username);
        createBillEmptyUser(consumer);
    }
    public void editAddress(String address){
        Bill bill = getCartUser();
        bill.setAddress(address);
        saveBill(bill);
    }
    public void editPhone(String phone){
        Bill bill = getCartUser();
        bill.setPhone(phone);
        saveBill(bill);
    }
    public void addBillDetail(CreateBillDetailRequest createBillDetailRequest){
        Bill bill = getCartUser();
        BillDetail billDetail = new BillDetail();
        billDetail.setIdBill(bill.getId());
        billDetail.setIdShoeDetail(createBillDetailRequest.getIdShoeDetail());
        billDetail.setQuantity(createBillDetailRequest.getQuantity());
        billDetail.setPrice(createBillDetailRequest.getPrice());
        bill.setTotal(billDetail.getPrice() * billDetail.getQuantity() + bill.getTotal());
        saveBill(bill);
        saveBillDetail(billDetail);
    }
    public BillDetail getBillDetailById(Long id) { return billDetailRepository.findById(id).get(); }
    public void deleteBillDetail(Long id){
        Bill bill = getCartUser();
        BillDetail billDetail = getBillDetailById(id);
        bill.setTotal(bill.getTotal() - billDetail.getPrice() * billDetail.getQuantity());
        saveBill(bill);
        billDetailRepository.delete(billDetail);
    }
    private Bill getCartUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Consumer consumer = consumerService.getConsumerByUsername(username);
        return billRepository.findOneByIdConsumerAndBillTypeAndCart(consumer.getId(), BillType.Export, true);
    }
}
