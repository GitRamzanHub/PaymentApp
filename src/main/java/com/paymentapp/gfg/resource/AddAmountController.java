package com.paymentapp.gfg.resource;

import com.paymentapp.gfg.entity.Transactions;
import com.paymentapp.gfg.entity.User;
import com.paymentapp.gfg.service.TransactionService;
import com.paymentapp.gfg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat.UUID;

@Controller
@RequestMapping("/addamount")
public class AddAmountController {

    @Autowired
    UserService userService;
    @Autowired
    TransactionService transactionService;


    String user_id;
    String accountNumber;

    @GetMapping
    public ModelAndView showAddAmountPage(@AuthenticationPrincipal OAuth2User principal){
        User blankUser = new User();
        ModelAndView modelAndView = new ModelAndView();
        String username = principal.getAttribute("name");


        Optional<User> dbUser = userService.getUserByUserName(username);


        if(!dbUser.isEmpty()){

            modelAndView.addObject("user", dbUser);
            user_id = String.valueOf(dbUser.get().getUserId());
            modelAndView.addObject("userId", user_id);
            accountNumber = dbUser.get().getAccountNumber();
            modelAndView.addObject("accountNumber", accountNumber);
            modelAndView.addObject("availableBalance", dbUser.get().getAvailableBalance());
        }

        modelAndView.setViewName("addamount");

        return modelAndView;
    }

    @PostMapping
    public String addAmount(@ModelAttribute User formUser){
        ModelAndView modelAndView = new ModelAndView();

        String message = "";
        // taking user from formUser by userId
        Optional<User> dbUser = userService.getUserById(formUser.getUserId()); // todo with set this user amount with updated amount
        long depositAmount = formUser.getAvailableBalance();

        if(dbUser.isPresent()) {
            // add deposit amount into dbUser
            long amount = dbUser.get().getAvailableBalance();
            amount += depositAmount;
            formUser.setAvailableBalance(amount);
            formUser.setBankName(dbUser.get().getBankName());
            formUser.setBranchName(dbUser.get().getBranchName());
            formUser.setUsername(dbUser.get().getUsername());
            formUser.setName(dbUser.get().getName());

            Transactions transactions = new Transactions();
            transactions.setAmount(depositAmount);
            transactions.setReceiverName(dbUser.get().getUsername());
            transactions.setSenderName(dbUser.get().getUsername());
            transactions.setTransactionType("Deposit");
            transactions.setUser(formUser);


            transactionService.save(transactions);

            userService.save(formUser);
        } else {
            return "redirect:/addamount?error";
        }

        return "redirect:/addamount?success";
    }
}
