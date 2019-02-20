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

    public ApiRestController(GoogleSheetsToMatrix htmlToMatrix) {
        this.googleSheetsToMatrix = htmlToMatrix;
    }

    @GetMapping
    public Matrix register(@RequestParam(name = "id") @Pattern(regexp = "[a-zA-Z0-9-_]+") String spreadsheetId,
                           @RequestParam(name = "sheet", defaultValue = "1") int sheetNumber) throws IOException {
        return googleSheetsToMatrix.convert(spreadsheetId, sheetNumber);
    }

}
