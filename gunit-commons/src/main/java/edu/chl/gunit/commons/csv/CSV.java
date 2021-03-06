package edu.chl.gunit.commons.csv;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davida on 5.2.2015.
 */
public class CSV {
    private final List<String> headers = new ArrayList<String>();
    private final List<List<String>> rows = new ArrayList<List<String>>();
    public void addHeader(String header) {
        headers.add(header);
    }

    public boolean hasHeader() {
        return headers.size() > 0;
    }

    public int getColumnCount() {
        return headers.size();
    }

    public String header(int idx) {
        return headers.get(idx);
    }

    public List<String> row(int idx) {
        return rows.get(idx);
    }

    public void addRow(List<String> row) {
        rows.add(row);
    }

    public List<List<String>> getRows() {
        return rows;
    }

}
