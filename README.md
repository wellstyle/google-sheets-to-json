# Google Spreadsheet to REST API

## Prerequisite

- JDK 8+

## Run

```bash
$ ./mvnw spring-boot:run
```

## Usage

__Step 1.__ You must publish your spreadsheet to the web, using `File -> Publish To Web` in your Google Spreadsheet.

__Step 2.__ Copy `<spreadsheetId>` from spreadsheet URL. `https://docs.google.com/spreadsheets/d/<spreadsheetId>/edit#gid=0`

__Step 3.__ Access readable REST API using the `/api` endpoint.

```
http://localhost:8080/api?id=<spreadsheetId>&sheet=<sheetNumber>
```

### Parameters

- __spreadsheetId(required)__: The ID of your document. This is the big long aplha-numeric code in the middle of your document URL.
- __sheetNumber(optional)__: The number of the individual sheet you want to get data from. Your first sheet is 1, your second sheet is 2, etc. If no sheet is entered then 1 is the default.

## Example

### Request

```
http://localhost:8080/api?id=1sGMmQgWOhc_i_Gp1OJ9OVB41iN5rV4o8d-6YPCb4z7I
```

### Response

```json
{
  "columns": {
    "country": [
      "South Korea",
      "China",
      "Japan"
    ],
    "city": [
      "Seoul",
      "Beijing",
      "Tokyo"
    ]
  },
  "rows": [
    {
      "country": "South Korea",
      "city": "Seoul"
    },
    {
      "country": "China",
      "city": "Beijing"
    },
    {
      "country": "Japan",
      "city": "Tokyo"
    }
  ]
}
```