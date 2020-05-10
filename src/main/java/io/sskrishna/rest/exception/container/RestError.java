package io.sskrishna.rest.exception.container;

import io.sskrishna.rest.exception.RestException;
import io.sskrishna.rest.exception.detail.ErrorDetail;
import io.sskrishna.rest.exception.detail.StatusErrorDetail;
import io.sskrishna.rest.response.ErrorCodeLookup;
import io.sskrishna.rest.response.RestResponse;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Data
public class RestError extends RestResponse {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    protected transient final ErrorCodeLookup errorCodeLookup;

    private String code;
    private String message;
    private String devMessage;

    public RestError(ErrorCodeLookup errorCodeLookup) {
        this.errorCodeLookup = errorCodeLookup;
    }

    public RestError withStatus(int status) {
        super.setStatus(status);
        return this;
    }

    public RestError withCode(String code) {
        ErrorDetail errorDetail = this.errorCodeLookup.getErrorCode(code);
        this.code = code;
        this.message = errorDetail.getMessage();
        this.devMessage = errorDetail.getDevMessage();
        if (errorDetail instanceof StatusErrorDetail) {
            if (super.getStatus() == 0) {
                super.setStatus(((StatusErrorDetail) errorDetail).getStatus());
            }
        }
        return this;
    }

    public boolean hasErrors() {
        return StringUtils.isEmpty(this.code) == false;
    }

    public void throwIfContainsErrors() {
        if (this.hasErrors()) {
            this.throwError();
        }
    }

    public void throwError() {
        throw new RestException(this);
    }
}
