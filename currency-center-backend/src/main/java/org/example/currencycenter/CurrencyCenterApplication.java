package org.example.currencycenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.Locale;

@SpringBootApplication
public class CurrencyCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyCenterApplication.class, args);

    }



}
