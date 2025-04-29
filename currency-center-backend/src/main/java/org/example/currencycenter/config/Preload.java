package org.example.currencycenter.config;

import org.example.currencycenter.model.*;
import org.example.currencycenter.repository.BalanceRepository;
import org.example.currencycenter.repository.CurrencyRepository;
import org.example.currencycenter.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Configuration
public class Preload  {


    @Bean
    CommandLineRunner commandLineRunner(CurrencyRepository currencyRepository, EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, BalanceRepository balanceRepository){
        return args -> {
            balanceRepository.saveAll(
                    List.of(
                            new Balance("PLN",2500),
                            new Balance("USD",1000),
                            new Balance("EUR",9912)
                    )
            );
            balanceRepository.addAmount("EUR",3);
            Employee user1 = new Employee();
            user1.setUsername("mike");
            user1.setPassword(passwordEncoder.encode("1234"));
            employeeRepository.save(user1);

            List<Currency> currencies = new ArrayList<>();

            currencies.add(new Currency("USD", "US Dollar", 4.11, 4.50));
            currencies.add(new Currency("EUR", "Euro", 4.35, 4.75));
            currencies.add(new Currency("GBP", "British Pound", 5.05, 5.50));
            currencies.add(new Currency("CHF", "Swiss Franc", 4.60, 5.00));
            currencies.add(new Currency("JPY", "Japanese Yen", 0.03, 0.03));
            currencies.add(new Currency("AUD", "Australian Dollar", 2.65, 2.95));
            currencies.add(new Currency("CAD", "Canadian Dollar", 2.95, 3.30));
            currencies.add(new Currency("SEK", "Swedish Krona", 0.36, 0.40));
            currencies.add(new Currency("NOK", "Norwegian Krone", 0.38, 0.42));
            currencies.add(new Currency("DKK", "Danish Krone", 0.58, 0.63));
            currencies.add(new Currency("CZK", "Czech Koruna", 0.17, 0.20));
            currencies.add(new Currency("HUF", "Hungarian Forint", 0.01, 0.01));
            currencies.add(new Currency("CNY", "Chinese Yuan", 0.55, 0.65));
            currencies.add(new Currency("TRY", "Turkish Lira", 0.13, 0.16));
            currencies.add(new Currency("INR", "Indian Rupee", 0.05, 0.05));


            currencyRepository.saveAll(currencies);






        };
    }
}
