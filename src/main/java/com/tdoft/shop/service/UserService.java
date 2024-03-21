package com.tdoft.shop.service;

import com.tdoft.shop.entity.user.User;
import com.tdoft.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public User registerUser(String email, String password) {
        return repository.save(
                new User()
                        .setEmail(email)
                        .setPassword(passwordEncoder.encode(password))
        );
    }

    public User save(User user) {
        return repository.save(user);
    }

    public User getCurrentUserOrThrow() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.findByEmail(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("No logged user found"));
    }

}
