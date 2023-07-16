package com.paymentapp.gfg.resource;

import com.paymentapp.gfg.entity.User;
import com.paymentapp.gfg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
public class DashboardController {

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public ModelAndView showDashboard(@AuthenticationPrincipal OAuth2User principal){
        ModelAndView modelAndView = new ModelAndView();
        String username = principal.getAttribute("name");
        modelAndView.addObject("username", username);
        String message = "";
        // checking weather user is register into DB or not.
        Optional<User> dbUser = userService.getUserByUserName(username);

        if(!dbUser.isEmpty()){
            message = "The web application you're describing is a financial platform that allows users to send money, receive money, and deposit funds without any transaction fees. It offers a convenient and cost-effective solution for managing financial transactions online.\n" +
                    "\n" +
                    "Key features of this web application may include:\n" +
                    "\n" +
                    "1. Money Transfers: Users can initiate money transfers to other individuals or businesses directly from their bank accounts or digital wallets. The platform facilitates secure and quick transactions, ensuring that funds reach the intended recipients promptly.\n" +
                    "\n" +
                    "2. Receive Money: Users can receive funds from others by providing their account details or sharing a unique payment link. The application simplifies the process of collecting payments from various sources.\n" +
                    "\n" +
                    "3. Deposit Funds: The web application enables users to deposit money into their accounts without any additional charges. This feature allows individuals to store their funds securely and access them whenever needed.\n" +
                    "\n" +
                    "4. Seamless Integration: The application may integrate with different payment systems, banks, and financial institutions to provide a wide range of options for sending, receiving, and depositing funds. This allows users to connect their existing accounts or wallets with the platform for a seamless experience.\n" +
                    "\n" +
                    "5. User-Friendly Interface: The web application is designed with a user-friendly interface, making it easy for individuals with varying levels of technical expertise to navigate and perform financial transactions effortlessly. Clear instructions and intuitive design elements enhance the user experience.";
            modelAndView.addObject("user", dbUser);
        }
        else{
            message = "User "+username+" is not registered, please register.";
        }

        modelAndView.addObject("message", message);
        modelAndView.setViewName("dashboard");
        return modelAndView;
    }

}
