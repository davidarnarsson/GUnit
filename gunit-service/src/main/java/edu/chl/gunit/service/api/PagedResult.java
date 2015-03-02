package edu.chl.gunit.service.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by davida on 2.3.2015.
 */
public class PagedResult<T> {
    private int offset;
    private int count;
    private final int total;
    private T value;

    public PagedResult(int offset, int count, int total, T value) {
        this.offset = offset;
        this.count = count;
        this.total = total;
        this.value = value;
    }

    @JsonProperty
    public int getOffset() {
        return offset;
    }

    @JsonProperty
    public int getCount() {
        return count;
    }

    @JsonProperty
    public T getValue() {
        return value;
    }

    @JsonProperty
    public int getTotal() {
        return total;
    }
}
