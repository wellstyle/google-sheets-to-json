package com.joonee.googlesheetstojson.converter;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CsvToMatrix implements GoogleSheetsToMatrix {
    private static final String URL_TEMPLATE = "https://docs.google.com/spreadsheets/d/{id}/pub?output=csv";

    @Override
    public Matrix convert(String spreadsheetId, int sheetNumber) throws IOException {

        Map<String, List<String>> columns = new HashMap<>();
        List<Map<String, String>> rows = new ArrayList<>();

        InputStream in = new URL(URL_TEMPLATE.replace("{id}", spreadsheetId)).openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        // columns
        String[] keys = br.readLine().split(",");
        for (String key : keys) {
            columns.put(key, new ArrayList<>());
        }

        // rows
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            Map<String, String> row = new HashMap<>();
            for (int num = 0; num < values.length; num++) {
                row.put(keys[num], values[num]);
                columns.get(keys[num]).add(values[num]);
            }
            rows.add(row);
        }
        return new Matrix(columns, rows);
    }
}
