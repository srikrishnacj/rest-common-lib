package io.sskrishna.rest.exception.container;

import io.sskrishna.rest.exception.container.validations.ObjectFieldValidator;
import io.sskrishna.rest.exception.container.validations.StringFieldValidator;
import io.sskrishna.rest.exception.detail.ErrorDetail;
import io.sskrishna.rest.exception.detail.FormErrorDetail;
import io.sskrishna.rest.response.ErrorCodeLookup;

public class FormError extends MultiRestError {

    FormError(ErrorCodeLookup errorCodeLookup) {
        super(errorCodeLookup);
    }

    public void rejectField(String field, String code) {
        ErrorDetail errorDetail = this.errorCodeLookup.getErrorCode(code);
        this.addExceptionDetail(field, errorDetail);
    }

    public void rejectIfEmpty(int subject, String field, String code) {
        if (subject == 0) {
            this.rejectField(field, code);
        }
    }

    public void rejectIfEmpty(long subject, String field, String code) {
        if (subject == 0) {
            this.rejectField(field, code);
        }
    }

    public void rejectIfEmpty(Iterable subject, String field, String code) {
        if (subject == null) {
            this.rejectField(field, code);
            return;
        }
        if (subject.iterator().hasNext()) {
            return;
        } else {
            this.rejectField(field, code);
        }
    }

    public boolean containsFieldError(String field) {
        for (ErrorDetail errorDetail : super.getErrors()) {
            FormErrorDetail detail = (FormErrorDetail) errorDetail;
            if (detail.getField().equals(field)) {
                return true;
            }
        }
        return false;
    }

    private void addExceptionDetail(String field, ErrorDetail errorDetail) {
        ErrorDetail detail = new FormErrorDetail(field, errorDetail);
        super.addErrorDetail(detail);
    }

    public StringFieldValidator stringValidator() {
        return new StringFieldValidator(this);
    }

    public ObjectFieldValidator objectValidator() {
        return new ObjectFieldValidator(this);
    }
}