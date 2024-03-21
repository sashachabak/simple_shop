package com.tdoft.shop.service;

import com.amazonaws.services.s3.AmazonS3;
import com.tdoft.shop.entity.user.Role;
import com.tdoft.shop.entity.user.Status;
import com.tdoft.shop.entity.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@Slf4j
public class BootstrapService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AmazonS3 amazonS3Client;

    @PostConstruct
    public void init() {
        userService.save(createUser());

//        amazonS3Client.listObjects("tdoft.tk").getObjectSummaries().forEach(
//                s3ObjectSummary -> log.info(s3ObjectSummary.getKey())
//        );
    }

    private User createUser() {
        return new User()
                .setEmail("test@mail.com")
                .setPassword(passwordEncoder.encode("test"))
                .setRole(Role.ADMIN)
                .setStatus(Status.ACTIVE);
    }

}
