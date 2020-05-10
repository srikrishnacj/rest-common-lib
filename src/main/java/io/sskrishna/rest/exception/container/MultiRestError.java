package io.sskrishna.rest.exception.container;

import io.sskrishna.rest.exception.detail.ErrorDetail;
import io.sskrishna.rest.response.ErrorCodeLookup;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

@Data
public class MultiRestError extends RestError {

    private Set<ErrorDetail> errors = new HashSet<>();

    MultiRestError(ErrorCodeLookup errorCodeLookup) {
        super(errorCodeLookup);
    }

    public boolean hasErrors() {
        boolean hasSuperError = super.hasErrors();
        return this.errors.size() > 0 || hasSuperError;
    }

    public void addError(String code) {
        ErrorDetail errorDetail = this.errorCodeLookup.getErrorCode(code);
        this.addErrorDetail(errorDetail);
    }

    protected void addErrorDetail(ErrorDetail detail) {
        this.errors.add(detail);
    }

    public void throwIfContainsErrors(int defaultStatus, String defaultCode) {
        if (this.hasErrors()) {
            this.setCodeIfNotSet(defaultCode).setStatusIfNotSet(defaultStatus);
            super.throwError();
        }
    }

    public void throwIfContainsErrors(String defaultCode) {
        if (this.hasErrors()) {
            this.setCodeIfNotSet(defaultCode);
            super.throwError();
        }
    }

    private MultiRestError setStatusIfNotSet(int status) {
        if (super.getStatus() == 0) {
            super.setStatus(status);
        }
        return this;
    }

    private MultiRestError setCodeIfNotSet(String code) {
        if (StringUtils.isEmpty(super.getCode())) {
            super.withCode(code);
        }
        return this;
    }
}