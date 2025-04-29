package org.example.currencycenter.repository;

import jakarta.transaction.Transactional;
import org.example.currencycenter.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance,Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Balance b SET b.amount = b.amount + :amount WHERE b.currency_code=:currencyCode")
    int addAmount(String currencyCode, double amount);

    @Transactional
    @Modifying
    @Query("UPDATE Balance b SET b.amount = b.amount - :amount WHERE b.currency_code=:currencyCode")
    int subtractAmount(String currencyCode, double amount);

    @Query("SELECT b.amount FROM Balance b WHERE b.currency_code=:currency")
    double getAmount(String currency);


}
