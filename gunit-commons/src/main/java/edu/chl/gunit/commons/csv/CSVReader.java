package edu.chl.gunit.commons.csv;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by davida on 5.2.2015.
 */
public class CSVReader {
    private final String delimiter;
    private final String newLineDelimiter  = System.getProperty("line.separator");

    public CSVReader() {
        delimiter = ",";
    }

    public CSVReader(String delimiter) {
        this.delimiter = delimiter;
    }

    public CSV read(String data, boolean hasHeaders) {
        CSV csv = new CSV();
        if (hasHeaders) {
            data = parseHeaders(data, csv);
        }

        if (data.length() > 0) {
            String[] rows = data.split(newLineDelimiter);

            for (String rowdata : rows) {
                csv.addRow(new ArrayList<String>(Arrays.asList(rowdata.split(delimiter))));
            }
        }

        return csv;
    }

    private String parseHeaders(String data, CSV csv) {
        int newLine = data.indexOf(newLineDelimiter);
        int delimiterLength  = newLineDelimiter.length();

        // it's the entire string if no newline found
        if (newLine == -1) {
            newLine = data.length();
            delimiterLength = 0;
        }

        String firstLine = data.substring(0, newLine);

        String[] headers = firstLine.split(delimiter);
        for(String header : headers) {
            csv.addHeader(header);
        }

        return data.substring(newLine + delimiterLength);
    }


}
