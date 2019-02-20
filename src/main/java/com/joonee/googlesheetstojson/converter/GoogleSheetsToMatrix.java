package com.joonee.googlesheetstojson.converter;

import java.io.IOException;

public interface GoogleSheetsToMatrix {

    Matrix convert(String spreadsheetId, int sheetNumber) throws IOException;

}
