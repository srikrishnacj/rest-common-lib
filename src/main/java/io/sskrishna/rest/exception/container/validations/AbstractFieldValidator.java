package io.sskrishna.rest.exception.container.validations;

import io.sskrishna.rest.exception.container.FormError;
import io.sskrishna.rest.model.JpaModel;

public abstract class AbstractFieldValidator<T extends AbstractFieldValidator<T>> extends SkippableFieldAware<T> {

    public AbstractFieldValidator(FormError formError) {
        super(formError);
    }

    public AbstractFieldValidator(AbstractFieldValidator validator) {
        super(validator);
    }

    public static interface MyPredicate {
        boolean test();
    }

    public interface ModelProducer {
        JpaModel produce(String id);
    }

    public T rejectField(String code) {
        String field = super.getField();
        FormError formError = super.getFormError();

        if (super.shouldSkipRejection()) return (T) this;
        formError.rejectField(field, code);
        return (T) this;
    }

    public T rejectOn(String code, MyPredicate predicate) {
        if (super.shouldSkipRejection()) return (T) this;
        boolean reject = predicate.test();
        if (reject) {
            rejectField(code);
        }
        return (T) this;
    }

    public StringFieldValidator stringValidator() {
        return new StringFieldValidator(this);
    }

    public ObjectFieldValidator objectValidator() {
        return new ObjectFieldValidator(this);
    }
}
