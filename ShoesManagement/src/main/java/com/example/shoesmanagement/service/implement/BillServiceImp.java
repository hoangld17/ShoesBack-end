package com.example.shoesmanagement.service.implement;

import com.example.shoesmanagement.dto.response.BillDetailResponse;
import com.example.shoesmanagement.dto.response.BillResponse;
import com.example.shoesmanagement.exception.ApplicationException;
import com.example.shoesmanagement.model.Bill;
import com.example.shoesmanagement.model.BillDetail;
import com.example.shoesmanagement.model.Consumer;
import com.example.shoesmanagement.model.ShoeDetail;
import com.example.shoesmanagement.model.enums.TypeBill;
import com.example.shoesmanagement.repository.BillDetailRepository;
import com.example.shoesmanagement.repository.BillRepository;
import com.example.shoesmanagement.repository.ConsumerRepository;
import com.example.shoesmanagement.repository.ShoesDetailRepository;
import com.example.shoesmanagement.repository.specification.BillSpecification;
import com.example.shoesmanagement.service.BillService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BillServiceImp implements BillService {
    @Autowired
    private ConsumerRepository consumerRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillSpecification billSpecification;
    @Autowired
    private BillDetailRepository billDetailRepository;
    @Autowired
    private ShoesDetailRepository shoesDetailRepository;

    @Override
    public BillResponse addBill(Bill bill) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final Consumer consumer = consumerRepository.findByUsername(username);

        if (consumer == null)
            throw new ApplicationException(HttpStatus.NOT_FOUND, "The consumer not found");
        bill.setConsumerUsername(username);
        bill.setPhone(consumer.getPhone());
        bill.setAddress(consumer.getAddress());
        bill.setEmail(consumer.getEmail());
        billRepository.save(bill);
        final BillResponse response = new BillResponse();
        response.setPurchaseDate(bill.getPurchaseDate());
        response.setConsumerUsername(consumer.getUsername());
        response.setAddress(consumer.getAddress());
        response.setPhone(consumer.getPhone());
        response.setId(bill.getId());
        return response;
    }

    @Override
    public List<BillResponse> getAllPayedBills() {
        List<BillResponse> responses = new ArrayList<>();
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();

        final Specification<Bill> billSpecification = this.billSpecification.doFilterPayedBills(username);
        final List<Bill> bills = billRepository.findAll(billSpecification);

        for (Bill b : bills) {
            responses.add(mapBillResponse(username, b));
        }
        return responses;
    }

    @Override
    public List<BillResponse> getAllBillsByUsername(String username) {
        final List<Bill> bills = billRepository.findAllByConsumerUsername(username);
        List<BillResponse> responses = new ArrayList<>();

        for (Bill b : bills) {
            responses.add(mapBillResponse(username, b));
        }
        return responses;

    }

    @Override
    public BillResponse getCart() {
        BillResponse billResponse = new BillResponse();
        String USERNAME = SecurityContextHolder.getContext().getAuthentication().getName();
        final Specification<Bill> billSpecification = this.billSpecification.doFilterBillDetailByCart(USERNAME);
        final List<Bill> bills = billRepository.findAll(billSpecification);
        if (bills.isEmpty())
            throw new ApplicationException(HttpStatus.NOT_FOUND, "Cart is empty");
        final List<BillDetail> billDetails = billDetailRepository.findAllByIdBill(bills.get(0).getId());
        final List<BillDetailResponse> detailResponses = getBillDetailResponses(billDetails, USERNAME);
        final Consumer consumer = consumerRepository.findByUsername(USERNAME);
        if (consumer == null)
            throw new ApplicationException(HttpStatus.NOT_FOUND, "The consumer not found");
        billResponse.setPhone(consumer.getPhone());
        billResponse.setAddress(consumer.getAddress());
        billResponse.setBillDetailResponses(detailResponses);
        billResponse.setConsumerUsername(USERNAME);
        billResponse.setBillType(TypeBill.Export);
        billResponse.setId(bills.get(0).getId());
        return billResponse;

    }

    @Override
    public BillResponse getBillById(Long id) {
        final Bill bill = billRepository.findOneById(id);
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (bill == null || !bill.getConsumerUsername().equals(username))
            throw new ApplicationException(HttpStatus.NOT_FOUND, "Bill not found");
        return getBillResponse(id, bill);
    }

    @Override
    public BillResponse getBillByIdAdmin(Long id) {
        final Bill bill = billRepository.findOneById(id);
        if (bill == null)
            throw new ApplicationException(HttpStatus.NOT_FOUND, "Bill not found");
        return getBillResponse(id, bill);
    }


    @Override
    public Bill saveBill(Bill bill) {
        return billRepository.save(bill);
    }


    @NotNull
    private BillResponse getBillResponse(Long id, Bill bill) {
        final BillResponse response = new BillResponse();
        response.setPurchaseDate(bill.getPurchaseDate());
        response.setConsumerUsername(bill.getConsumerUsername());
        response.setAddress(bill.getAddress());
        response.setPhone(bill.getPhone());
        response.setId(bill.getId());
        response.setBillType(bill.getBillType());
        response.setId(bill.getId());
        response.setTotal(bill.getTotal());

        final List<BillDetail> billDetails = billDetailRepository.findAllByIdBill(id);
        final List<BillDetailResponse> detailResponses = getBillDetailResponses(billDetails, bill.getConsumerUsername());
        response.setBillDetailResponses(detailResponses);
        return response;
    }

    private ShoeDetail getShoeDetail(Long id) {
        final ShoeDetail oneById = shoesDetailRepository.findOneById(id);
        //Redundant if work with font end
        if (oneById == null)
            throw new ApplicationException(HttpStatus.NOT_FOUND, "The shoe not found");
        return oneById;
    }

    private BillResponse mapBillResponse(String username, Bill b) {
        BillResponse bResponse = new BillResponse();
        final List<BillDetail> billDetails = billDetailRepository.findAllByIdBill(b.getId());
        final List<BillDetailResponse> list = getBillDetailResponses(billDetails, b.getConsumerUsername());
        bResponse.setConsumerUsername(username);
        bResponse.setBillType(b.getBillType());
        bResponse.setAddress(b.getAddress());
        bResponse.setTotal(b.getTotal());
        bResponse.setPurchaseDate(b.getPurchaseDate());
        bResponse.setBillDetailResponses(list);
        bResponse.setPhone(b.getPhone());
        bResponse.setId(b.getId());
        bResponse.setCart(b.isCart());
        return bResponse;
    }

    private List<BillDetailResponse> getBillDetailResponses(List<BillDetail> billDetails, String username) {
        return billDetails.stream()
                .map(billDetail -> {
                    BillDetailResponse response = new BillDetailResponse();
                    response.setId(billDetail.getId());
                    response.setUsername(username);
                    response.setQuantity(billDetail.getQuantity());
                    response.setIdBill(billDetail.getIdBill());
                    response.setShoeDetail(getShoeDetail(billDetail.getIdShoeDetail()));
                    return response;
                })
                .collect(Collectors.toList());
    }
}
