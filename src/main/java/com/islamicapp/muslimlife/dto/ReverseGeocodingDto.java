package com.islamicapp.muslimlife.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

// Root response for Reverse Geocoding
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReverseGeocodingDto {
    @JsonProperty("plus_code")
    private PlusCode plusCode;
    private List<GeocodingResult> results;
    private String status;
}
// Plus Code
@Data
@NoArgsConstructor
@AllArgsConstructor
class PlusCode {
    @JsonProperty("compound_code")
    private String compoundCode;
    @JsonProperty("global_code")
    private String globalCode;
}

// Individual geocoding result
@Data
@NoArgsConstructor
@AllArgsConstructor
class GeocodingResult {
    @JsonProperty("address_components")
    private List<GeocodingAddressComponent> addressComponents;
    @JsonProperty("formatted_address")
    private String formattedAddress;
    private Geometry geometry;
    @JsonProperty("place_id")
    private String placeId;
    private List<String> types;
}

// Address component
@Data
@NoArgsConstructor
@AllArgsConstructor
class GeocodingAddressComponent {
    @JsonProperty("long_name")
    private String longName;
    @JsonProperty("short_name")
    private String shortName;
    private List<String> types;
}

// Geometry information
@Data
@NoArgsConstructor
@AllArgsConstructor
class Geometry {
    private Bounds bounds;
    private GeoLocation location;
    @JsonProperty("location_type")
    private String locationType;
    private Viewport viewport;
}

// Bounds
@Data
@NoArgsConstructor
@AllArgsConstructor
class Bounds {
    private GeoCoordinate northeast;
    private GeoCoordinate southwest;
}

// Viewport
@Data
@NoArgsConstructor
@AllArgsConstructor
class Viewport {
    private GeoCoordinate northeast;
    private GeoCoordinate southwest;
}

// Location (lat/lng)
@Data
@NoArgsConstructor
@AllArgsConstructor
class GeoLocation {
    private Double lat;
    private Double lng;
}

// Coordinate (for bounds and viewport)
@Data
@NoArgsConstructor
@AllArgsConstructor
class GeoCoordinate {
    private Double lat;
    private Double lng;
}