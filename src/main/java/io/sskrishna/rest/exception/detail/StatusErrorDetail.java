package io.sskrishna.rest.exception.detail;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StatusErrorDetail extends ErrorDetail {
    int status;

    public StatusErrorDetail(int status, String code, String msg, String devMsg) {
        super(code, msg, devMsg);
        this.status = status;
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