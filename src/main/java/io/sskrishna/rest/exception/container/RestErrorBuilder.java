package io.sskrishna.rest.exception.container;

import io.sskrishna.rest.response.ErrorCodeLookup;

public class RestErrorBuilder {
    private final ErrorCodeLookup errorCodeLookup;

    public RestErrorBuilder(ErrorCodeLookup errorCodeLookup) {
        this.errorCodeLookup = errorCodeLookup;
    }

    public RestError restError() {
        return new RestError(this.errorCodeLookup);
    }

    public MultiRestError multiRestError() {
        return new MultiRestError(this.errorCodeLookup);
    }

    public FormError formError() {
        return new FormError(this.errorCodeLookup);
    }
}
