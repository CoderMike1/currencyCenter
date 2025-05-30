package org.example.currencycenter.repository;

import org.example.currencycenter.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    @Query("SELECT SUM(t.exchangedAmount) FROM Transaction t")
    BigDecimal getTotalExchangedAmount();

    @Query("SELECT SUM(t.exchangedAmount) FROM Transaction t WHERE t.type = 'BUY'")
    BigDecimal getTotalBUYExchangedAmount();

    @Query("SELECT SUM(t.exchangedAmount) FROM Transaction t WHERE t.type = 'SELL'")
    BigDecimal getTotalSELLExchangedAmount();

}
