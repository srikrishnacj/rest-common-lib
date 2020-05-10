package io.sskrishna.rest.exception.detail;

import lombok.Data;

import java.util.Objects;

@Data
public class FormErrorDetail extends ErrorDetail {
    private String field;

    public FormErrorDetail(String field, ErrorDetail errorDetail) {
        super(errorDetail.getCode(), errorDetail.getMessage(), errorDetail.getDevMessage());
        this.field = field;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}