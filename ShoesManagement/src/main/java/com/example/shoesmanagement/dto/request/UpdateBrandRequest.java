package com.example.shoesmanagement.dto.request;

import com.example.shoesmanagement.model.util.ParamError;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UpdateBrandRequest {
    private String name;
    private String phone;
    private String email;
    private String imgUrl;
}