package com.islamicapp.muslimlife.service;

import com.islamicapp.muslimlife.dto.MetalPriceDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class MetalApiService {

    @Autowired
    @Qualifier("metalPriceRestClient")
    private RestClient restClient;
    static List<String> accessToken = Arrays.asList("goldapi-3drhusmgdnyb51-io", "goldapi-4fshw19mhw58ytq-io", "goldapi-1ig19mhw59ygx-io");
    static int val = 2;

    public MetalPriceDto getCurrentMetalPrice(String metalSymbol, String currencyCode) {
        val = (val+1)%3;
        return restClient.get()
                .uri("/api/"+metalSymbol+"/"+currencyCode)
                .header("x-access-token", accessToken.get(val))
                .retrieve()
                .body(MetalPriceDto.class);
    }
}
