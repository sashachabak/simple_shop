package com.tdoft.shop.dto.response;

import com.tdoft.shop.entity.user.AddressDetails;
import com.tdoft.shop.entity.user.ContactInfo;
import com.tdoft.shop.entity.user.User;
import lombok.Data;

@Data
public class UserContactInfoDto {

    private ContactInfo contactInfo;
    private AddressDetails addressDetails;

    public UserContactInfoDto(User user) {
        this.contactInfo = user.getContactInfo();
        this.addressDetails = user.getAddressDetails();
    }
}
