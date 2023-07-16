package com.paymentapp.gfg.service;

import com.paymentapp.gfg.entity.Transactions;
import com.paymentapp.gfg.entity.User;
import com.paymentapp.gfg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Service
public class UserService{

    @Autowired
    UserRepository userRepository;

    // method to get user by username
    public Optional<User> getUserByUserName(String username){

        return StreamSupport.stream(userRepository.findAll().spliterator(),false)
                .filter(x->x.getUsername().equals(username)).findFirst();
    }
//    public void registerUser(UserRegistrationDTO user) {
//        userRepository.save(user);
//    }

    // method to get User by accountNumber
    public Optional<User> getUserByAccountNumber(String accountNumber){
        return StreamSupport.stream(userRepository.findAll().spliterator(),false)
                .filter(x->x.getAccountNumber().equals(accountNumber)).findFirst();
    }


    public User save(User formUser) {
        return userRepository.save(formUser);
    }

    public Optional<User> getUserById(UUID userId){
        return userRepository.findById(userId);
    }

}
