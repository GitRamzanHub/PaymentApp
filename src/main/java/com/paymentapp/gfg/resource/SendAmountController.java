package com.paymentapp.gfg.resource;

import com.paymentapp.gfg.entity.SendAmountData;
import com.paymentapp.gfg.entity.Transactions;
import com.paymentapp.gfg.entity.User;
import com.paymentapp.gfg.service.TransactionService;
import com.paymentapp.gfg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.web.servlet.oauth2.login.OAuth2LoginSecurityMarker;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;
import java.util.UUID;

@Controller
public class SendAmountController {

    @Autowired
    UserService userService;

    @Autowired
    TransactionService transactionService;

    @GetMapping("/transfer")
    public ModelAndView showSendAmountPage(@AuthenticationPrincipal OAuth2User principal) {
        SendAmountData sendAmountData = new SendAmountData();
        ModelAndView modelAndView = new ModelAndView();

        String username = principal.getAttribute("name");

        // getting user id from username
        Optional<User> dbUser = userService.getUserByUserName(username);

        if (dbUser.isPresent()) {
            // get user id
            UUID userId = dbUser.get().getUserId();
            sendAmountData.setUserId(userId);
            modelAndView.addObject("userId", userId);
            modelAndView.addObject("balance", dbUser.get().getAvailableBalance());
        }
        modelAndView.addObject("sendAmountData",sendAmountData);
        modelAndView.setViewName("transfer");

        return modelAndView;
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public String processTransfer(@ModelAttribute("sendAmountData") SendAmountData data) {

        User updatedSender = new User();
        User updatedReceiver = new User();

        // Get user from userId from which amount should be debited
        Optional<User> sender = userService.getUserById(data.getUserId());

        // Get user from account Number from which amount should be credited
        Optional<User> receiver = userService.getUserByAccountNumber(data.getAccountNumber());
        System.out.println("Received the sender & Receiver from database");
        if (sender.isPresent()) {
            if(data.getAmount() == 0){
                return "redirect:/transfer?zero";
            }
            // 1. Debit amount from sender
            long senderAvailableBalance = sender.get().getAvailableBalance();
            if (data.getAmount() > senderAvailableBalance) {
                return "redirect:/transfer?error";
            } else {
                senderAvailableBalance -= data.getAmount();
                updatedSender.setUserId(sender.get().getUserId());
                updatedSender.setUsername(sender.get().getUsername());
                updatedSender.setName(sender.get().getName());
                updatedSender.setAccountNumber(sender.get().getAccountNumber());
                updatedSender.setBankName(sender.get().getBankName());
                updatedSender.setBranchName(sender.get().getBranchName());
                updatedSender.setAvailableBalance(senderAvailableBalance);
                // 2. save the debited amount user back again to the database
                userService.save(updatedSender);


                // 3. make the transaction entry and save the transaction into transaction table
                Transactions senderTransaction = new Transactions();
                senderTransaction.setAmount(data.getAmount());
                senderTransaction.setReceiverName(receiver.get().getUsername());
                senderTransaction.setSenderName(sender.get().getUsername());
                senderTransaction.setTransactionType("Debited");
                senderTransaction.setUser(updatedSender);

                transactionService.save(senderTransaction);
            }

        } else {
            return "redirect:/transfer?senderNotFound";
        }
        if (receiver.isPresent()) {

            //4. credit the amount into receiver account
            long receiverAvailableBalance = receiver.get().getAvailableBalance();
            receiverAvailableBalance += data.getAmount();

            // 5. update the receiver into database
            updatedReceiver.setUserId(receiver.get().getUserId());
            updatedReceiver.setUsername(receiver.get().getUsername());
            updatedReceiver.setName(receiver.get().getName());
            updatedReceiver.setAccountNumber(receiver.get().getAccountNumber());
            updatedReceiver.setBankName(receiver.get().getBankName());
            updatedReceiver.setBranchName(receiver.get().getBranchName());
            updatedReceiver.setAvailableBalance(receiverAvailableBalance);

            userService.save(updatedReceiver);

            // 6. make the transaction entry into database for receiver

            Transactions receiverTransaction = new Transactions();
            receiverTransaction.setAmount(data.getAmount());
            receiverTransaction.setReceiverName(receiver.get().getUsername());
            receiverTransaction.setSenderName(sender.get().getUsername());
            receiverTransaction.setTransactionType("Credited");
            receiverTransaction.setUser(updatedReceiver);

            transactionService.save(receiverTransaction);

        } else {
            return "redirect:/transfer?receiverNotFound";
        }
        return "redirect:/transfer?success";
    }
}

//
