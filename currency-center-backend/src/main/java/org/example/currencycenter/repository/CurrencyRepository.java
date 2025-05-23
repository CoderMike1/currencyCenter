package org.example.currencycenter.repository;

import jakarta.transaction.Transactional;
import org.example.currencycenter.dto.UpdateExchangeRate;
import org.example.currencycenter.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {

    @Transactional
    @Modifying
    @Query("UPDATE Currency c SET c.buyRate = :buy_rate , c.sellRate = :sell_rate WHERE c.code = :currency_code")
    int updateValues(String currency_code, BigDecimal buy_rate, BigDecimal sell_rate);


    @Query("SELECT c.code FROM Currency c")
    List<String> getAllCurrencies();

    @Query("SELECT c FROM Currency c WHERE c.code != 'PLN'")
    List<Currency> getAll();

}
