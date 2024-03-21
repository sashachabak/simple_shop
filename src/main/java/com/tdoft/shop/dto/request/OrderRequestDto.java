package com.tdoft.shop.dto.request;

import com.tdoft.shop.entity.user.AddressDetails;
import com.tdoft.shop.entity.user.ContactInfo;
import lombok.Data;

@Data
public class OrderRequestDto {

    private Long goodId;
    private ContactInfo contactInfo;
    private AddressDetails addressDetails;

}
