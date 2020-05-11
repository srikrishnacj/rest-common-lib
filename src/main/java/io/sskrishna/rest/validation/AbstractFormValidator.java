package io.sskrishna.rest.validation;

import io.sskrishna.rest.exception.container.FormError;
import io.sskrishna.rest.model.JpaModel;

public abstract class AbstractFormValidator<T extends JpaModel> implements CrudValidator<T> {
    @Override
    public void create(T entity) {
        FormError formError = this.formError();
        this.validateCreate(formError, entity);
        formError.throwIfContainsErrors(422, "form.invalid");
    }

    @Override
    public void update(T entity) {
        FormError formError = this.formError();
        this.validateUpdate(formError, entity);
        formError.throwIfContainsErrors(422, "form.invalid");
    }

    @Override
    public void delete(String id) {
        FormError formError = this.formError();
        this.validateDelete(formError, id);
        formError.throwIfContainsErrors(422, "form.invalid");
    }

    @Override
    public void findOne(String id) {
        FormError formError = this.formError();
        this.validateFindOne(formError, id);
        formError.throwIfContainsErrors(422, "form.invalid");
    }

    public abstract void validateCreate(FormError formError, T entity);

    public abstract void validateUpdate(FormError formError, T entity);

    public abstract void validateDelete(FormError formError, String id);

    public abstract void validateFindOne(FormError formError, String id);
}
