package com.islamicapp.muslimlife.controller;

import com.islamicapp.muslimlife.dto.MetalPriceDto;
import com.islamicapp.muslimlife.response.IslamicResponse;
import com.islamicapp.muslimlife.service.AllServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/all")
public class AllController {

    @Autowired
    private AllServiceApi allServiceApi;

    @GetMapping("/metal/price/{metal}/{currency}")
    public ResponseEntity<IslamicResponse<MetalPriceDto>> getMetalPrice(@PathVariable String metal, @PathVariable String currency){

        IslamicResponse<MetalPriceDto> islamicResponse =  allServiceApi.getMetalPrice(metal, currency);

        return new ResponseEntity<>(islamicResponse, HttpStatus.OK);

    }
}
