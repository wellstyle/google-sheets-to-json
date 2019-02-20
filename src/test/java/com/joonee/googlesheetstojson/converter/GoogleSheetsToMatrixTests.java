package com.joonee.googlesheetstojson.converter;

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
    private static final int SHEET_0_SIZE = 3;
    private static final String SHEET_0_FIRST_COLUMN_NAME = "city";
    private static final String SHEET_0_SECOND_COLUMN_NAME = "country";
    private static final int SHEET_1_SIZE = 5;
    private static final String SHEET_1_FIRST_COLUMN_NAME = "가수명";
    private static final String SHEET_1_SECOND_COLUMN_NAME = "대표곡";

    @Test
    public void test_HtmlToJson_Ok_1() throws IOException {
        GoogleSheetsToMatrix json = new HtmlToMatrix();
        Matrix matrix = json.convert(SPREADSHEET_ID, 0);
        log.debug("### {}", matrix);

        assertThat(matrix.getRows().size()).isEqualTo(SHEET_0_SIZE);
        assertThat(matrix.getColumns().get(SHEET_0_FIRST_COLUMN_NAME).size()).isEqualTo(SHEET_0_SIZE);
        assertThat(matrix.getColumns().get(SHEET_0_SECOND_COLUMN_NAME).size()).isEqualTo(SHEET_0_SIZE);
    }

    @Test
    public void test_HtmlToJson_Ok_2() throws IOException {
        GoogleSheetsToMatrix json = new HtmlToMatrix();
        Matrix matrix = json.convert(SPREADSHEET_ID, 1);
        log.debug("### {}", matrix);

        assertThat(matrix.getRows().size()).isEqualTo(SHEET_1_SIZE);
        assertThat(matrix.getColumns().get(SHEET_1_FIRST_COLUMN_NAME).size()).isEqualTo(SHEET_1_SIZE);
        assertThat(matrix.getColumns().get(SHEET_1_SECOND_COLUMN_NAME).size()).isEqualTo(SHEET_1_SIZE);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_HtmlToJson_IndexOutOfBoundsException() throws IOException {
        GoogleSheetsToMatrix json = new HtmlToMatrix();
        Matrix matrix = json.convert(SPREADSHEET_ID, 4);
        log.debug("### {}", matrix);
    }

    @Test(expected = HttpStatusException.class)
    public void test_HtmlToJson_HttpStatusException() throws IOException {
        GoogleSheetsToMatrix json = new HtmlToMatrix();
        Matrix matrix = json.convert(NOT_FOUND_SPREADSHEET_ID, 0);
    }

    @Test
    public void test_CsvToJson() throws IOException {
        GoogleSheetsToMatrix json = new CsvToMatrix();
        Matrix matrix = json.convert(SPREADSHEET_ID, 0);
        log.debug("### {}", matrix);

        assertThat(matrix.getRows().size()).isEqualTo(SHEET_0_SIZE);
        assertThat(matrix.getColumns().get(SHEET_0_FIRST_COLUMN_NAME).size()).isEqualTo(SHEET_0_SIZE);
        assertThat(matrix.getColumns().get(SHEET_0_SECOND_COLUMN_NAME).size()).isEqualTo(SHEET_0_SIZE);
    }

    @Test(expected = FileNotFoundException.class)
    public void test_CsvToJson_NotfoundResource() throws IOException {
        GoogleSheetsToMatrix json = new CsvToMatrix();
        Matrix matrix = json.convert(NOT_FOUND_SPREADSHEET_ID, 0);
    }

}
