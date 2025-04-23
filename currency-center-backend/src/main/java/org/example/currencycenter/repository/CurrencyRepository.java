package org.example.currencycenter.repository;

import jakarta.transaction.Transactional;
import org.example.currencycenter.dto.UpdateExchangeRate;
import org.example.currencycenter.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {

    @Transactional
    @Modifying
    @Query("UPDATE Currency c SET c.buy_rate = :buy_rate , c.sell_rate = :sell_rate WHERE c.code = :currency_code")
    int updateValues(String currency_code, double buy_rate, double sell_rate);


}
