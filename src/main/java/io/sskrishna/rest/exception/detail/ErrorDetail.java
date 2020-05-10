package io.sskrishna.rest.exception.detail;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class ErrorDetail {
    private String code;
    private String message;
    private String devMessage;

    public ErrorDetail(String code, String message, String devMessage) {
        this.code = code;
        this.message = message;
        this.devMessage = devMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorDetail that = (ErrorDetail) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}