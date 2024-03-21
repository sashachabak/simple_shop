package com.tdoft.shop.api.auth;

import com.tdoft.shop.dto.response.UserContactInfoDto;
import com.tdoft.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/user")
//@CrossOrigin
@RestController
public class UserApi {

    @Autowired
    private UserService userService;

    @GetMapping("/current-user/user-info")
    public UserContactInfoDto getCurrentUserInfo() {
        return new UserContactInfoDto(userService.getCurrentUserOrThrow());
    }

}
