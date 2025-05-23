package org.example.currencycenter.config;


import org.example.currencycenter.model.Balance;
import org.example.currencycenter.model.Currency;
import org.example.currencycenter.model.Employee;
import org.example.currencycenter.repository.BalanceRepository;
import org.example.currencycenter.repository.CurrencyRepository;
import org.example.currencycenter.repository.EmployeeRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class AppLoadData implements ApplicationRunner {

    private BalanceRepository balanceRepository;
    private PasswordEncoder passwordEncoder;
    private EmployeeRepository employeeRepository;
    private CurrencyRepository currencyRepository;

    public AppLoadData(BalanceRepository balanceRepository,PasswordEncoder passwordEncoder,EmployeeRepository employeeRepository,
                       CurrencyRepository currencyRepository){
        this.balanceRepository = balanceRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {


        Employee user1 = new Employee();
        user1.setUsername("mike");
        user1.setPassword(passwordEncoder.encode("1234"));
        employeeRepository.save(user1);

        List<Currency> currencies = new ArrayList<>();

        currencies.add(new Currency("USD", "US Dollar", new BigDecimal("4.11"), new BigDecimal("4.50")));
        currencies.add(new Currency("EUR", "Euro", new BigDecimal("4.35"), new BigDecimal("4.75")));
        currencies.add(new Currency("GBP", "British Pound", new BigDecimal("5.05"), new BigDecimal("5.50")));
        currencies.add(new Currency("CHF", "Swiss Franc", new BigDecimal("4.60"), new BigDecimal("5.00")));
        currencies.add(new Currency("JPY", "Japanese Yen", new BigDecimal("0.03"), new BigDecimal("0.03")));
        currencies.add(new Currency("AUD", "Australian Dollar", new BigDecimal("2.65"), new BigDecimal("2.95")));
        currencies.add(new Currency("CAD", "Canadian Dollar", new BigDecimal("2.95"), new BigDecimal("3.30")));
        currencies.add(new Currency("SEK", "Swedish Krona", new BigDecimal("0.36"), new BigDecimal("0.40")));
        currencies.add(new Currency("NOK", "Norwegian Krone", new BigDecimal("0.38"), new BigDecimal("0.42")));
        currencies.add(new Currency("DKK", "Danish Krone", new BigDecimal("0.58"), new BigDecimal("0.63")));
        currencies.add(new Currency("CZK", "Czech Koruna", new BigDecimal("0.17"), new BigDecimal("0.20")));
        currencies.add(new Currency("HUF", "Hungarian Forint", new BigDecimal("0.01"), new BigDecimal("0.01")));
        currencies.add(new Currency("CNY", "Chinese Yuan", new BigDecimal("0.55"), new BigDecimal("0.65")));
        currencies.add(new Currency("TRY", "Turkish Lira", new BigDecimal("0.13"), new BigDecimal("0.16")));
        currencies.add(new Currency("INR", "Indian Rupee", new BigDecimal("0.05"), new BigDecimal("0.05")));
        currencies.add(new Currency("PLN", "Polish Zloty", new BigDecimal("1.00"), new BigDecimal("1.00")));



        currencyRepository.saveAll(currencies);


        Map<String,BigDecimal> savedWallet = Map.of("PLN", new BigDecimal("2500"), "USD", new BigDecimal("1000"), "EUR", new BigDecimal("900"));

        List<String> codes = currencyRepository.getAllCurrencies();

        List<Balance> balanceList = new LinkedList<>();

        for(String code : codes){
            if(savedWallet.get(code) != null){
                balanceList.add(new Balance(code,savedWallet.get(code)));
            }
            else{
                balanceList.add(new Balance(code, new BigDecimal("0.0")));
            }
        }
        balanceRepository.saveAll(balanceList);

    }
}
