package com.joonee.googlesheetstojson.converter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@ToString
@AllArgsConstructor
public class Matrix {
    private Map<String, List<String>> columns;
    private List<Map<String, String>> rows;
}
