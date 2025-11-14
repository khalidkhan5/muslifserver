package com.islamicapp.muslimlife.service;

import com.islamicapp.muslimlife.dto.MetalPriceDto;
import com.islamicapp.muslimlife.response.IslamicResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AllServiceApi {

    @Autowired
    private MetalApiService metalApiService;

    static Map<String, MetalPriceDto> metalPriceDtoMap = new HashMap<>();

    static Map<String, Long> metalTime = new HashMap<>();

    public IslamicResponse<MetalPriceDto> getMetalPrice(String metal, String currency){
         String metalCurrency = metal+":"+currency;
        if(metalTime.containsKey(metalCurrency) && metalPriceDtoMap.containsKey(metalCurrency)){
            Long prevTime = metalTime.get(metalCurrency);
            Long currTime = System.currentTimeMillis();
            Long diffTime = currTime - prevTime;
            if(diffTime <86400000L){
                return new IslamicResponse<>(metalPriceDtoMap.get(metalCurrency));
            }
        }

        MetalPriceDto metalPriceDto = metalApiService.getCurrentMetalPrice(metal, currency);

        metalTime.put(metalCurrency, System.currentTimeMillis());
        metalPriceDtoMap.put(metalCurrency, metalPriceDto);

        return new IslamicResponse<>(metalPriceDto);
    }
}
