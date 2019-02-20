package com.joonee.googlesheetstojson.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiErrorResponse {
    private String error;
    private String error_description;
}
