package com.paymentapp.gfg.repository;

import com.paymentapp.gfg.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, UUID> {

//    @Query("SELECT t FROM Transactions t WHERE t.user.user_id = :user_id")
//    List<Transactions> findByUserId(@Param("user_id") UUID user_id);
}
