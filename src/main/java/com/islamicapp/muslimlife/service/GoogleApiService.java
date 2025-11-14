package com.islamicapp.muslimlife.service;

import com.islamicapp.muslimlife.dto.MetalPriceDto;
import com.islamicapp.muslimlife.dto.PlaceDetailsDto;
import com.islamicapp.muslimlife.dto.PlacesAutoCompleteDto;
import com.islamicapp.muslimlife.dto.ReverseGeocodingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

public class GoogleApiService {
    @Autowired
    @Qualifier("placeRestClient")
    private RestClient placeRestClient;

    @Autowired
    @Qualifier("mapRestClient")
    private RestClient mapRestClient;

    @Value("${google.api.key}")
    private String googleApiKey;

    public PlacesAutoCompleteDto getAutoCompletePlaces(String query) {
        return placeRestClient.post()
                .uri("/v1/places:autocomplete")
                .header("Content-Type", "application/json")
                .header("X-Goog-Api-Key", googleApiKey)
                .body(Map.of(
                        "input", query,
                        "includedPrimaryTypes", List.of("locality", "administrative_area_level_1", "administrative_area_level_2", "country"),
                        "languageCode", "en"
                ))
                .retrieve()
                .body(PlacesAutoCompleteDto.class);
    }

    public PlaceDetailsDto getPlaceDetails(String placeId) {
        return placeRestClient.get()
                .uri("/v1/places/" + placeId)
                .header("Content-Type", "application/json")
                .header("X-Goog-Api-Key", googleApiKey)
                .header("X-Goog-FieldMask", "location,displayName,formattedAddress,addressComponents")
                .retrieve()
                .body(PlaceDetailsDto.class);
    }

    public ReverseGeocodingDto reverseGeocode(double latitude, double longitude) {
        String uri = UriComponentsBuilder
                .fromPath("/maps/api/geocode/json")
                .queryParam("latlng", String.format("%.6f,%.6f", latitude, longitude))
                .queryParam("result_type", "locality|administrative_area_level_1|administrative_area_level_2|country")
                .queryParam("key", googleApiKey)
                .toUriString();

        return mapRestClient.get()
                .uri(uri)
                .retrieve()
                .body(ReverseGeocodingDto.class);
    }

}
