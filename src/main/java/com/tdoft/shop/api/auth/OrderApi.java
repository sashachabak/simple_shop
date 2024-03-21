package com.tdoft.shop.api.auth;

import com.tdoft.shop.dto.request.OrderRequestDto;
import com.tdoft.shop.entity.Good;
import com.tdoft.shop.entity.user.User;
import com.tdoft.shop.exception.GoodNotFoundException;
import com.tdoft.shop.service.GoodService;
import com.tdoft.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderApi {

    private final UserService userService;
    private final GoodService goodService;

    @PostMapping
    public void makeOrder(@RequestBody OrderRequestDto orderRequest) {
        Good good = goodService.findById(orderRequest.getGoodId()).orElseThrow(() -> new GoodNotFoundException(orderRequest.getGoodId()));
        User user = userService.getCurrentUserOrThrow();
        user.getOrderedGoods().add(good);
        user
                .setContactInfo(orderRequest.getContactInfo())
                .setAddressDetails(orderRequest.getAddressDetails());

        userService.save(user);
    }

}
