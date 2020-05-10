package io.sskrishna.rest.validation;

import io.sskrishna.rest.exception.container.FormError;
import io.sskrishna.rest.model.JpaModel;

public class JpaBaseFormValidator<T extends JpaModel> extends AbstractModelValidator<T> {

    @Override
    public void validateCreate(FormError formError, T entity) {
    }

    @Override
    public void validateUpdate(FormError formError, T entity) {
        this.ensureIdExist(formError, entity.getId());
    }

    @Override
    public void validateDelete(FormError formError, String id) {
        this.ensureIdExist(formError, id);
    }

    @Override
    public void validateFindOne(FormError formError, String id) {
        this.ensureIdExist(formError, id);
    }

    private void ensureIdExist(FormError formError, String id) {
        formError.stringValidator()
                .field("id")
                .value(id)
                .rejectIfEmpty("resource.id.required");
    }
}
