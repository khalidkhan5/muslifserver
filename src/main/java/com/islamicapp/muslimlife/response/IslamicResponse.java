package com.islamicapp.muslimlife.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IslamicResponse<T> {
    private T data;
}
