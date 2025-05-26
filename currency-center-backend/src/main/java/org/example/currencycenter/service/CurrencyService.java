package org.example.currencycenter.service;


import org.example.currencycenter.dto.UpdateExchangeRate;
import org.example.currencycenter.exception.CurrencyNotFoundException;
import org.example.currencycenter.exception.QueryDBException;
import org.example.currencycenter.model.Currency;
import org.example.currencycenter.repository.CurrencyRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class CurrencyService {
    private CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository){
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> getAllCurrenciesRates(){
        return currencyRepository.getAll();
    }

    public Currency getCode(String code){
        Currency c = currencyRepository.findById(code).orElseThrow(() -> new CurrencyNotFoundException("currency not found"));
        return c;
    }

    public boolean updateRate(String currency_code, UpdateExchangeRate newRates){
        currencyRepository.findById(currency_code).orElseThrow(() -> new CurrencyNotFoundException("currency not found"));
        int updated = currencyRepository.updateValues(currency_code,newRates.buy_rate(),newRates.sell_rate());
        if(updated == 0){
            throw new QueryDBException("Error while updating value.");
        }
        else{
            return true;
        }
    }


    public HashMap<String,Double> getRatesFromNBP(){
        String url = "https://api.nbp.pl/api/exchangerates/tables/A?format=json";
        HashMap<String,Double> array = new LinkedHashMap<>();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url)).GET().build();
        try{
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            JSONArray jsonArray = new JSONArray(response.body());

            JSONArray rates_array = jsonArray.getJSONObject(0).getJSONArray("rates");

            for(int i =0;i< rates_array.length();i++){
                JSONObject item = rates_array.getJSONObject(i);
                array.put(item.getString("code"), item.getDouble("mid"));
            }
        }
        catch(IOException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return array;
    }

    public boolean updateAllPricesBasedOnNBP(BigDecimal percent){
        if(percent.compareTo(BigDecimal.ZERO)<0){
            return false;
        }
        try{
            HashMap<String,Double> rates = getRatesFromNBP();

            for(Map.Entry<String,Double> r : rates.entrySet()){
                Optional<Currency> optional = currencyRepository.findById(r.getKey());
                if(!optional.isPresent()){
                    continue;
                }
                else{
                    BigDecimal value = BigDecimal.valueOf(r.getValue());
                    BigDecimal profit = value.multiply(percent).divide(BigDecimal.valueOf(100),10, RoundingMode.HALF_UP);

                    BigDecimal new_buy_rate = value.subtract(profit).setScale(2,RoundingMode.HALF_UP);

                    BigDecimal new_sell_rate = value.add(profit).setScale(2,RoundingMode.HALF_UP);

                    currencyRepository.updateValues(r.getKey(),new_buy_rate,new_sell_rate);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }





}
