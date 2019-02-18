package com.joonee.googlesheetstojson.converter;

import com.joonee.googlesheetstojson.converter.CsvToMatrix;
import com.joonee.googlesheetstojson.converter.GoogleSheetsToMatrix;
import com.joonee.googlesheetstojson.converter.HtmlToMatrix;
import com.joonee.googlesheetstojson.converter.Matrix;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.HttpStatusException;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class GoogleSheetsToMatrixTests {

    private static final String SPREADSHEET_ID = "1sGMmQgWOhc_i_Gp1OJ9OVB41iN5rV4o8d-6YPCb4z7I";
    private static final String NOT_FOUND_SPREADSHEET_ID = "1sGMmQgWOhc_i_Gp1OJ9OVB41iN5rV4o8d-6YPCb4z7";
    private static final int SIZE = 5;
    private static final String FIRST_COLUMN_NAME = "city";
    private static final String SECOND_COLUMN_NAME = "country";

    @Test
    public void test_HtmlToJson() throws IOException {
        GoogleSheetsToMatrix json = new HtmlToMatrix();
        Matrix matrix = json.convert(SPREADSHEET_ID);
        log.debug("### {}", matrix);

        assertThat(matrix.getRows().size()).isEqualTo(SIZE);
        assertThat(matrix.getColumns().get(FIRST_COLUMN_NAME).size()).isEqualTo(SIZE);
        assertThat(matrix.getColumns().get(SECOND_COLUMN_NAME).size()).isEqualTo(SIZE);
    }

    @Test(expected = HttpStatusException.class)
    public void test_HtmlToJson_NotfoundResource() throws IOException {
        GoogleSheetsToMatrix json = new HtmlToMatrix();
        Matrix matrix = json.convert(NOT_FOUND_SPREADSHEET_ID);
    }

    @Test
    public void test_CsvToJson() throws IOException {
        GoogleSheetsToMatrix json = new CsvToMatrix();
        Matrix matrix = json.convert(SPREADSHEET_ID);
        log.debug("### {}", matrix);

        assertThat(matrix.getRows().size()).isEqualTo(SIZE);
        assertThat(matrix.getColumns().get(FIRST_COLUMN_NAME).size()).isEqualTo(SIZE);
        assertThat(matrix.getColumns().get(SECOND_COLUMN_NAME).size()).isEqualTo(SIZE);
    }

    @Test(expected = FileNotFoundException.class)
    public void test_CsvToJson_NotfoundResource() throws IOException {
        GoogleSheetsToMatrix json = new CsvToMatrix();
        Matrix matrix = json.convert(NOT_FOUND_SPREADSHEET_ID);
    }

}
