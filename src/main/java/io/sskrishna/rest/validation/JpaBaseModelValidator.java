package io.sskrishna.rest.validation;

import io.sskrishna.rest.exception.container.FormError;
import io.sskrishna.rest.model.JpaModel;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public class JpaBaseModelValidator<T extends JpaModel> extends AbstractModelValidator<T> {
    private final JpaRepository<T, String> repository;

    public JpaBaseModelValidator(JpaRepository<T, String> repository) {
        this.repository = repository;
    }

    @Override
    public void validateCreate(FormError formError, T entity) {
        this.ensureIdDoesNotExist(formError, entity.getId());
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

    private void ensureIdDoesNotExist(FormError formError, String id) {
        formError
                .objectValidator()
                .field("id")
                .valueAndRootValue(id)
                .skipIfRootValueIsEmpty()
                .value(() -> this.repository.findById(id))
                .rejectIfNotNull("resource.id.exists");
    }

    private void ensureIdExist(FormError formError, String id) {
        formError.objectValidator()
                .field("id")
                .value(() -> this.repository.findById(id))
                .rejectIfNull("resource.id.not.exists");
    }

    protected String nullSafeFindByIdPluckId(String id) {
        if (id == null) return null;
        Optional<T> temp = this.repository.findById(id);
        if (temp.isPresent()) {
            return temp.get().getId();
        }
        return null;
    }

    protected String pluckId(Optional optional) {
        if (optional == null) return null;
        if (optional.isPresent()) {
            if (optional.get() instanceof Persistable) {
                return ((Persistable) optional.get()).getId().toString();
            }
        }
        return null;
    }
}
