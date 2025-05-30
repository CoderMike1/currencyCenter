package org.example.currencycenter.config;


import org.example.currencycenter.model.*;
import org.example.currencycenter.model.Currency;
import org.example.currencycenter.repository.BalanceRepository;
import org.example.currencycenter.repository.CurrencyRepository;
import org.example.currencycenter.repository.EmployeeRepository;
import org.example.currencycenter.repository.TransactionRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class AppLoadData implements ApplicationRunner {

    private BalanceRepository balanceRepository;
    private PasswordEncoder passwordEncoder;
    private EmployeeRepository employeeRepository;
    private CurrencyRepository currencyRepository;
    private TransactionRepository transactionRepository;

    public AppLoadData(BalanceRepository balanceRepository,PasswordEncoder passwordEncoder,EmployeeRepository employeeRepository,
                       CurrencyRepository currencyRepository, TransactionRepository transactionRepository){
        this.balanceRepository = balanceRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
        this.currencyRepository = currencyRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {


        Employee emp = new Employee();
        emp.setUsername("mike");
        emp.setRole(ROLE.ADMIN);
        emp.setPassword(passwordEncoder.encode("1234"));
        employeeRepository.save(emp);

        Employee user2 = new Employee();
        user2.setUsername("josh");
        user2.setRole(ROLE.WORKER);
        user2.setPassword(passwordEncoder.encode("1234"));
        employeeRepository.save(user2);

        Employee user3 = new Employee();
        user3.setUsername("paul");
        user3.setRole(ROLE.WORKER);
        user3.setPassword(passwordEncoder.encode("1234"));
        employeeRepository.save(user3);

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
        Currency usd = currencies.get(0);
        Currency eur = currencies.get(1);
        List<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(TRANSACTION_TYPE.BUY, LocalDateTime.now().minusDays(33), new BigDecimal("100.00"), new BigDecimal("1.10"), new BigDecimal("110.00"), usd, emp));
        transactions.add(new Transaction(TRANSACTION_TYPE.SELL, LocalDateTime.now().minusDays(17), new BigDecimal("200.00"), new BigDecimal("0.90"), new BigDecimal("180.00"), eur, emp));
        transactions.add(new Transaction(TRANSACTION_TYPE.BUY, LocalDateTime.now().minusHours(11), new BigDecimal("300.00"), new BigDecimal("1.05"), new BigDecimal("315.00"), usd, emp));
        transactions.add(new Transaction(TRANSACTION_TYPE.SELL, LocalDateTime.now().minusDays(5), new BigDecimal("150.00"), new BigDecimal("0.95"), new BigDecimal("142.50"), eur, emp));
        transactions.add(new Transaction(TRANSACTION_TYPE.BUY, LocalDateTime.now().minusDays(7), new BigDecimal("500.00"), new BigDecimal("1.12"), new BigDecimal("560.00"), usd, emp));
        transactions.add(new Transaction(TRANSACTION_TYPE.SELL, LocalDateTime.now().minusHours(77), new BigDecimal("250.00"), new BigDecimal("0.85"), new BigDecimal("212.50"), eur, emp));
        transactions.add(new Transaction(TRANSACTION_TYPE.BUY, LocalDateTime.now().minusDays(300), new BigDecimal("400.00"), new BigDecimal("1.08"), new BigDecimal("432.00"), usd, emp));
        transactions.add(new Transaction(TRANSACTION_TYPE.SELL, LocalDateTime.now().minusHours(90), new BigDecimal("350.00"), new BigDecimal("0.88"), new BigDecimal("308.00"), eur, emp));
        transactions.add(new Transaction(TRANSACTION_TYPE.BUY, LocalDateTime.now().minusDays(20), new BigDecimal("600.00"), new BigDecimal("1.14"), new BigDecimal("684.00"), usd, emp));
        transactions.add(new Transaction(TRANSACTION_TYPE.SELL, LocalDateTime.now(), new BigDecimal("450.00"), new BigDecimal("0.92"), new BigDecimal("414.00"), eur, emp));

        transactionRepository.saveAll(transactions);

        System.out.println(transactionRepository.getTotalBUYExchangedAmount());


    }
}
