package io.sskrishna.rest.exception.container.validations;

import io.sskrishna.rest.exception.container.FormError;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class FormErrorAware<T extends FormErrorAware<T>> {
    private FormError formError;

    protected abstract T self();

    public FormErrorAware(FormError formError) {
        this.formError = formError;
    }

    public FormErrorAware(FormErrorAware abstractValidator) {
        this.formError = abstractValidator.formError;
    }
}