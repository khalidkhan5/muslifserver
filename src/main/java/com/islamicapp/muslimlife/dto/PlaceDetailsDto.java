package com.islamicapp.muslimlife.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

// Root response for Place Details
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDetailsDto {
    private String formattedAddress;
    private List<AddressComponent> addressComponents;
    private Location location;
    private DisplayName displayName;
}

// Address component details
@Data
@NoArgsConstructor
@AllArgsConstructor
class AddressComponent {
    private String longText;
    private String shortText;
    private List<String> types;
    private String languageCode;
}

// Location with latitude and longitude
@Data
@NoArgsConstructor
@AllArgsConstructor
class Location {
    private Double latitude;
    private Double longitude;
}

// Display name
@Data
@NoArgsConstructor
@AllArgsConstructor
class DisplayName {
    private String text;
    private String languageCode;
}