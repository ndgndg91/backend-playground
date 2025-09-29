package com.ndgndg91.cityservice;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public record ApiResponse<T>(
        @JsonUnwrapped T data
) {
    public static <T> ApiResponse<T> from(T data) {
        return new ApiResponse<>(data);
    }
}
