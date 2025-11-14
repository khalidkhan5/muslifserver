package com.islamicapp.muslimlife.service;

import com.islamicapp.muslimlife.dto.CreateUserRequest;
import com.islamicapp.muslimlife.dto.UpdateUserRequest;
import com.islamicapp.muslimlife.dto.UserDto;
import com.islamicapp.muslimlife.dto.UserListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    @Qualifier("userServiceRestClient")
    private final RestClient userServiceRestClient;

    // GET request example
    public UserDto getUser(Long userId) {
        return userServiceRestClient.get()
                .uri("/users/{id}", userId)
                .retrieve()
                .body(UserDto.class);
    }

    // GET with response entity (to access headers, status)
    public ResponseEntity<UserDto> getUserWithResponse(Long userId) {
        return userServiceRestClient.get()
                .uri("/users/{id}", userId)
                .retrieve()
                .toEntity(UserDto.class);
    }

    // POST request example
    public UserDto createUser(CreateUserRequest request) {
        return userServiceRestClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(UserDto.class);
    }

    // PUT request example
    public UserDto updateUser(Long userId, UpdateUserRequest request) {
        return userServiceRestClient.put()
                .uri("/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(UserDto.class);
    }

    // DELETE request example
    public void deleteUser(Long userId) {
        userServiceRestClient.delete()
                .uri("/users/{id}", userId)
                .retrieve()
                .toBodilessEntity();
    }

    // With query parameters
    public UserListDto getUsersByRole(String role, int page, int size) {
        return userServiceRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/users")
                        .queryParam("role", role)
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                .body(UserListDto.class);
    }

    // With custom headers
    public UserDto getUserWithAuth(Long userId, String token) {
        return userServiceRestClient.get()
                .uri("/users/{id}", userId)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(UserDto.class);
    }
}