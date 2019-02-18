package com.joonee.googlesheetstojson.converter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HtmlToMatrix implements GoogleSheetsToMatrix {
    private static final String URL_TEMPLATE = "https://docs.google.com/spreadsheets/d/{id}/pubhtml";

    @Override
    public Matrix convert(String spreadsheetId) throws IOException {
        Map<String, List<String>> columns = new HashMap<>();
        List<Map<String, String>> rows = new ArrayList<>();

        Document doc = Jsoup.connect(URL_TEMPLATE.replace("{id}", spreadsheetId)).get();
        Element sheet = doc.getElementById("sheets-viewport");
        Elements elements = sheet.child(0).select("table tbody tr");

        // columns
        String[] keys = elements.first().select("td")
            .stream()
            .map(Element::text)
            .toArray(String[]::new);
        elements.remove(0);
        for (String key : keys) {
            columns.put(key, new ArrayList<>());
        }

        // rows
        for (Element element : elements) {
            Map<String, String> data = new HashMap<>();
            for (int i = 0; i < element.select("td").size(); i++) {
                String value = element.select("td").get(i).text();
                data.put(keys[i], value);
                columns.get(keys[i]).add(value);
            }
            rows.add(data);
        }

        return new Matrix(columns, rows);
    }
}
