package com.paymentapp.gfg.resource;

import com.paymentapp.gfg.entity.Transactions;
import com.paymentapp.gfg.entity.User;
import com.paymentapp.gfg.service.TransactionService;
import com.paymentapp.gfg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.text.html.HTMLDocument;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/transactions")
public class TransactionsController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    UserService userService;

    @GetMapping
    public ModelAndView showTransactions(@AuthenticationPrincipal OAuth2User principal, Model mode){
        ModelAndView modelAndView = new ModelAndView();
        String username = principal.getAttribute("name");
        Optional<User> user = userService.getUserByUserName(username);
        UUID userId = user.get().getUserId();
        Iterable<Transactions> myTransaction = transactionService.getTransactionsByUserId(userId);
        Iterator<Transactions> myIterator = myTransaction.iterator();
        while (myIterator.hasNext()){
            System.out.println(myIterator.next());
        }
        System.out.println(myTransaction);
        modelAndView.addObject("transactions", myTransaction);
        modelAndView.setViewName("transactions");
        return modelAndView;
    }
}
