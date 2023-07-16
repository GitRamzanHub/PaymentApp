package com.paymentapp.gfg.service;

import com.paymentapp.gfg.entity.Transactions;
import com.paymentapp.gfg.entity.User;
import com.paymentapp.gfg.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    TransactionsRepository transactionsRepository;


    public Transactions save(Transactions transactions) {
        return transactionsRepository.save(transactions);
    }

    // get transaction List
    public Iterable<Transactions> getTransactionsByUserId(UUID userId){
        Iterable<Transactions> dbTransactions = transactionsRepository.findAll();

        List<Transactions> myTransactions = new ArrayList<>();

        for(Object ob : dbTransactions){
            myTransactions.add((Transactions) ob);
        }

        return myTransactions.stream().filter(x -> x.getUser().getUserId().equals(userId)).collect(Collectors.toList());
    }

}
