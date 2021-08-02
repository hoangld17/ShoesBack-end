package com.example.shoesmanagement.model;

import com.example.shoesmanagement.model.enums.TypeBill;
import com.example.shoesmanagement.model.util.Validator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
public class Bill extends AuditableDomain<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String consumerUsername;
    private Date purchaseDate;
    private TypeBill billType;
    private String phone;
    private String email;
    private String address;
    private boolean isCart;
    private double total;

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = Validator.convertDate(purchaseDate, "Purchase date");
    }

    public void setPhone(String phone) {
        Validator.checkPhoneFormat(phone);
        this.phone = phone;
    }

    public void setAddress(String address) {
        Validator.checkNullEmptyAndLength(address, 200, "Address");
        this.address = address;
    }
}
