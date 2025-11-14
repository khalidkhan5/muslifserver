package com.islamicapp.muslimlife.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlacesAutoCompleteDto {
    private List<Suggestion> suggestions;

}
// Suggestion wrapper
@Data
@NoArgsConstructor
@AllArgsConstructor
class Suggestion {
    private PlacePrediction placePrediction;
}

// Place prediction details
@Data
@NoArgsConstructor
@AllArgsConstructor
class PlacePrediction {
    private String place;
    private String placeId;
    private Text text;
    private StructuredFormat structuredFormat;
    private List<String> types;
}

// Text with matches
@Data
@NoArgsConstructor
@AllArgsConstructor
class Text {
    private String text;
    private List<Match> matches;
}

// Match offset
@Data
@NoArgsConstructor
@AllArgsConstructor
class Match {
    private Integer endOffset;
}

// Structured format for main and secondary text
@Data
@NoArgsConstructor
@AllArgsConstructor
class StructuredFormat {
    private MainText mainText;
    private SecondaryText secondaryText;
}

// Main text (city name)
@Data
@NoArgsConstructor
@AllArgsConstructor
class MainText {
    private String text;
    private List<Match> matches;
}

// Secondary text (state, country)
@Data
@NoArgsConstructor
@AllArgsConstructor
class SecondaryText {
    private String text;
}