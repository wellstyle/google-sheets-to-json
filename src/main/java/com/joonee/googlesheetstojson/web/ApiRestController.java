package com.joonee.googlesheetstojson.web;

import com.joonee.googlesheetstojson.converter.GoogleSheetsToMatrix;
import com.joonee.googlesheetstojson.converter.Matrix;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import java.io.IOException;

@RestController
@Validated
public class ApiRestController {

    private final GoogleSheetsToMatrix googleSheetsToMatrix;

    public ApiRestController(GoogleSheetsToMatrix csvToMatrix) {
        this.googleSheetsToMatrix = csvToMatrix;
    }

    @GetMapping
    public Matrix register(@Pattern(regexp = "[a-zA-Z0-9-_]+") @RequestParam String id) throws IOException {
        return googleSheetsToMatrix.convert(id);
    }
}
