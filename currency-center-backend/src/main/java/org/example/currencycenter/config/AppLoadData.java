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
        currencies.add(new Currency("PLN", "Polish Zloty", 1, 1));


        currencyRepository.saveAll(currencies);


        Map<String,Double> savedWallet = Map.of("PLN", 2500.0, "USD", 1000.0, "EUR", 900.0);

        List<String> codes = currencyRepository.getAllCurrencies();

        List<Balance> balanceList = new LinkedList<>();

        for(String code : codes){
            if(savedWallet.get(code) != null){
                balanceList.add(new Balance(code,savedWallet.get(code)));
            }
            else{
                balanceList.add(new Balance(code, 0.0));
            }
        }
        balanceRepository.saveAll(balanceList);

    }
}
