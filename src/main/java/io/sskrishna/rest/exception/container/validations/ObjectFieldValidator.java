package io.sskrishna.rest.exception.container.validations;

import io.sskrishna.rest.exception.container.FormError;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Persistable;

import java.util.Optional;

public class ObjectFieldValidator extends AbstractFieldValidator<ObjectFieldValidator> {
    public ObjectFieldValidator(FormError formError) {
        super(formError);
    }

    public ObjectFieldValidator(AbstractFieldValidator validator) {
        super(validator);
    }

    @Override
    protected ObjectFieldValidator self() {
        return this;
    }

    public ObjectFieldValidator rejectIfNull(String code) {
        if (super.shouldSkipRejection()) return self();
        Object value = super.value();

        if (value == null) {
            super.rejectField(code);
        } else if (value instanceof Optional) {
            if (((Optional) value).isPresent() == false) {
                super.rejectField(code);
            }
        }

        return self();
    }

    public ObjectFieldValidator rejectIfNotNull(String code) {
        if (super.shouldSkipRejection()) return self();
        Object value = super.value();

        if (value != null) {
            if (value instanceof Optional) {
                if (((Optional) value).isPresent()) {
                    super.rejectField(code);
                }
            } else {
                super.rejectField(code);
            }
        }
        return self();
    }

    public ObjectFieldValidator rejectIfEquals(String code, Object otherValue) {
        if (super.shouldSkipRejection()) return self();
        Object value = super.value();

        if (otherValue == null || value == null) return self();
        if (otherValue.equals(value)) {
            this.rejectField(code);
        }
        return self();
    }

    public ObjectFieldValidator rejectIfNotEquals(String code, Object otherValue) {
        if (shouldSkipRejection()) return self();
        Object value = super.value();

        if (otherValue == null || value == null) {
            return self();
        } else if (otherValue.equals(value) == false) {
            this.rejectField(code);
        }
        return self();
    }

    public ObjectFieldValidator rejectIfNotEquals(String code, TaskRunner taskRunner) {
        if (shouldSkipRejection()) return self();
        Object value = super.value();
        Object otherValue = taskRunner.run();

        if (otherValue == null || value == null) {
            return self();
        } else if (otherValue.equals(value) == false) {
            this.rejectField(code);
        }
        return self();
    }

    public ObjectFieldValidator rejectValueIfNotNullAndNotEqualsTo(String code, TaskRunner taskRunner) {
        Object otherValue = taskRunner.run();
        return this.rejectValueIfNotNullAndNotEqualsTo(code, otherValue);
    }

    public ObjectFieldValidator rejectValueIfNotNullAndNotEqualsTo(String code, Object otherValue) {
        if (super.shouldSkipRejection()) return self();
        Object value = super.value();
        if (value == null) return self();
        if (otherValue == null) return self();

        if (value.equals(otherValue) == false) {
            rejectField(code);
        }
        return self();
    }

    public ObjectFieldValidator rejectModelIfNotNullAndIdEmpty(String code) {
        if (shouldSkipRejection()) return self();
        Object value = super.value();
        if (value == null) return self();

        if (value instanceof Persistable) {
            Persistable<String> p = (Persistable) value;
            String id = p.getId();
            if (StringUtils.isEmpty(id)) {
                super.rejectField(code);
            }
        } else {
            throw new RuntimeException("model is not a subclass of Persistable.class");
        }

        return self();
    }
}
