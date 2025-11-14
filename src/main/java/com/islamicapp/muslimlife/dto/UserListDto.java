package com.islamicapp.muslimlife.dto;

import lombok.Data;

@Data
public class UserListDto {
    private java.util.List<UserDto> users;
    private int totalPages;
    private long totalElements;
}
