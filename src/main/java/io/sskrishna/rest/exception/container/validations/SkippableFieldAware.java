package io.sskrishna.rest.exception.container.validations;

import io.sskrishna.rest.exception.container.FormError;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

public abstract class SkippableFieldAware<T extends SkippableFieldAware<T>> extends FieldValueAware<T> {
    private Set<String> skipFields = new HashSet<>();
    private boolean skipIfValueIsNull;
    private boolean skipIfValueIsEmpty;
    private boolean skipIfRootValueIsNull;
    private boolean skipIfRootValueIsEmpty;

    public SkippableFieldAware(FormError formError) {
        super(formError);
    }

    public SkippableFieldAware(SkippableFieldAware validator) {
        super(validator);
        this.skipFields = validator.skipFields;
        this.skipIfValueIsNull = validator.skipIfValueIsNull;
        this.skipIfValueIsEmpty = validator.skipIfValueIsEmpty;
        this.skipIfRootValueIsNull = validator.skipIfRootValueIsNull;
        this.skipIfRootValueIsEmpty = validator.skipIfRootValueIsEmpty;
    }

    public T field(String name) {
        super.field(name);
        this.resetSkipFlags();
        return (T) this;
    }

    public T resetSkipFlags() {
        this.skipIfRootValueIsEmpty = false;
        this.skipIfRootValueIsNull = false;
        this.skipIfValueIsEmpty = false;
        this.skipIfValueIsNull = false;
        return (T) this;
    }

    public T value(Object value) {
        if (this.shouldSkipOnFirstError()) return (T) this;
        if (this.shouldSkipOnRootValue()) return (T) this;

        super.value(value);
        return (T) this;
    }

    public T value(TaskRunner taskRunner) {
        if (this.shouldSkipOnFirstError()) return (T) this;
        if (this.shouldSkipOnRootValue()) return (T) this;

        Object value = toValue(taskRunner);
        this.value(value);
        return (T) this;
    }

    public T rootValue(Object value) {
        super.rootValue(value);
        this.resetSkipFlags();
        return (T) this;
    }

    public T stopAfterFirstError() {
        super.assertFieldNotEmpty();
        this.skipFields.add(super.getField());
        return (T) this;
    }

    public T doNotStopAfterFirstError() {
        super.assertFieldNotEmpty();
        this.skipFields.remove(super.getField());
        return (T) this;
    }

    public T skipIfValueIsNull() {
        this.skipIfValueIsNull = true;
        return (T) this;
    }

    public T skipIfValueIsEmpty() {
        this.skipIfValueIsEmpty = true;
        return (T) this;
    }

    public T skipIfRootValueIsNull() {
        this.skipIfRootValueIsNull = true;
        return (T) this;
    }

    public T skipIfRootValueIsEmpty() {
        this.skipIfRootValueIsEmpty = true;
        return (T) this;
    }

    private boolean shouldSkipOnRootValue() {
        Object rootValue = super.getRootValue();
        if (skipIfRootValueIsNull && rootValue == null) return true;
        if (skipIfRootValueIsEmpty) {
            if (rootValue == null) return true;
            if (rootValue instanceof String) {
                return StringUtils.isEmpty((String) rootValue);
            }
        }
        return false;
    }

    private boolean shouldSkipOnValue() {
        Object value = super.value();
        if (skipIfValueIsNull && value == null) return true;
        if (skipIfValueIsEmpty) {
            if (value == null) return true;
            if (value instanceof String) {
                return StringUtils.isEmpty((String) value);
            }
        }
        return false;
    }

    private boolean shouldSkipOnFirstError() {
        if (getFormError().containsFieldError(super.getField())) return true;
        return false;
    }

    protected boolean shouldSkipRejection() {
        this.assertFieldNotEmpty();

        if (shouldSkipOnFirstError()) return true;
        if (shouldSkipOnRootValue()) return true;
        return shouldSkipOnValue();
    }
}