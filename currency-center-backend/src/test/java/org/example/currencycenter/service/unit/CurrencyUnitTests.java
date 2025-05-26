package org.example.currencycenter.service.unit;


import org.example.currencycenter.dto.UpdateExchangeRate;
import org.example.currencycenter.exception.CurrencyNotFoundException;
import org.example.currencycenter.exception.QueryDBException;
import org.example.currencycenter.model.Currency;
import org.example.currencycenter.repository.CurrencyRepository;
import org.example.currencycenter.service.CurrencyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurrencyUnitTests {

    @Mock
    private CurrencyRepository repo;

    @Spy
    @InjectMocks
    private CurrencyService service;


    @Test
    public void shouldReturnAllCurrencies(){
        List<Currency> expected = new ArrayList<>(List.of(new Currency("USD","US DOLLAR",new BigDecimal("5.11"), new BigDecimal("4.11"))));

        when(repo.getAll()).thenReturn(expected);

        List<Currency> actual = service.getAllCurrenciesRates();

        assertEquals(expected,actual);

    }
    @Test
    public void shouldReturnCurrencyCode(){
        String code = "EUR";
        Currency expected = new Currency("EUR", "Euro", new BigDecimal("4.35"), new BigDecimal("4.75"));

        when(repo.findById(code)).thenReturn(Optional.of(expected));

        Currency actual = service.getCode(code);

        assertEquals(expected,actual);
    }
    @Test
    public void shouldReturnCurrencyNotFoundExceptionWhileGettingCurrencyCode(){
        String code = "EUR";

        when(repo.findById(code)).thenThrow(new CurrencyNotFoundException("currency not found"));

        assertThrows(CurrencyNotFoundException.class, () -> service.getCode(code));

    }
    @Test
    public void shouldUpdateRateSuccessfully(){
        UpdateExchangeRate request = new UpdateExchangeRate(new BigDecimal("4.5"),new BigDecimal("1.0"));
        String code = "USD";
        when(repo.findById(code)).thenReturn(Optional.of(new Currency()));

        when(repo.updateValues(code,request.buy_rate(),request.sell_rate())).thenReturn(1);

        boolean actual = service.updateRate(code,request);
        assertTrue(actual);
    }
    @Test
    public void shouldThrowCurrencyNotFoundExceptionWhileUpdatingRate(){
        UpdateExchangeRate request = new UpdateExchangeRate(new BigDecimal("4.5"),new BigDecimal("1.0"));
        String code = "XYZ";
        when(repo.findById(code)).thenReturn(Optional.empty());

        assertThrows(CurrencyNotFoundException.class, () -> service.updateRate(code,request));
    }

    @Test
    public void shouldReturnQueryDBExceptionWhileUpdatingRate(){
        UpdateExchangeRate request = new UpdateExchangeRate(new BigDecimal("4.5"),new BigDecimal("1.0"));
        String code = "JPY";
        when(repo.findById(code)).thenReturn(Optional.of(new Currency()));
        when(repo.updateValues(code,request.buy_rate(),request.sell_rate())).thenReturn(0);

        assertThrows(QueryDBException.class, () -> service.updateRate(code,request));
    }

    @Test
    public void shouldUpdateRatesBasedOnBNP(){
        HashMap<String,Double> mocked_nbp_rates = new HashMap<>();
        mocked_nbp_rates.put("USD",3.47);
        mocked_nbp_rates.put("JPY",2.11);

        doReturn(mocked_nbp_rates).when(service).getRatesFromNBP();

        when(repo.findById("USD")).thenReturn(Optional.of(new Currency()));
        when(repo.findById("JPY")).thenReturn(Optional.of(new Currency()));

        boolean result = service.updateAllPricesBasedOnNBP(new BigDecimal("10"));

        assertTrue(result);

        verify(repo).updateValues(eq("USD"),eq(new BigDecimal("3.12")),eq(new BigDecimal("3.82")));
        verify(repo).updateValues(eq("JPY"), eq(new BigDecimal("1.90")), eq(new BigDecimal("2.32")));


    }

    @Test
    public void shouldNotUpdateRateBasedOnBNPIfCurrencyIsMissing(){
        Map<String,Double> mocked_nbp_rates = new HashMap<>();
        mocked_nbp_rates.put("CHF",11.0);
        doReturn(mocked_nbp_rates).when(service).getRatesFromNBP();

        when(repo.findById("CHF")).thenReturn(Optional.empty());

        boolean result = service.updateAllPricesBasedOnNBP(new BigDecimal("22"));

        assertTrue(result);

        verify(repo,never()).updateValues(any(),any(),any());
    }
    @Test
    public void shouldReturnFalseIfPercentLowerThanZero(){

        boolean result = service.updateAllPricesBasedOnNBP(new BigDecimal("-2"));

        assertFalse(result);
    }


}
