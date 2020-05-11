package io.sskrishna.rest.exception.container.validations;

import io.sskrishna.rest.exception.container.FormError;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@Data
public abstract class FieldValueAware<T extends FieldValueAware<T>> extends FormErrorAware<T> {
    private String field;
    private Object value;
    private Object rootValue;

    public FieldValueAware(FormError formError) {
        super(formError);
    }

    public FieldValueAware(FieldValueAware validator) {
        super(validator);
        this.field = validator.field;
        this.value = validator.value;
        this.rootValue = validator.rootValue;
    }

    public static interface TaskRunner {
        Object run();
    }

    public String field() {
        return this.field;
    }

    public T field(String name) {
        this.field = name;
        this.value = null;
        this.rootValue = null;
        return (T) this;
    }

    public Object value() {
        return this.value;
    }

    public T value(Object value) {
        this.value = toValue(value);
        return (T) this;
    }

    public T value(TaskRunner taskRunner) {
        Object value = toValue(taskRunner);
        this.value(value);
        return (T) this;
    }

    public T value(Object value, TaskRunner taskRunner) {
        value = toValue(value);
        if (value != null) {
            value = toValue(taskRunner);
        }
        this.value(value);
        return (T) this;
    }

    public Object rootValue() {
        return this.rootValue;
    }

    public T rootValue(Object value) {
        this.rootValue = toValue(value);
        return (T) this;
    }

    public T rootValue(TaskRunner taskRunner) {
        Object value = taskRunner.run();
        this.rootValue(value);
        return (T) this;
    }

    public T rootValue(Object value, TaskRunner taskRunner) {
        value = toValue(value);
        if (value != null) {
            value = toValue(taskRunner);
        }
        this.rootValue(value);
        return (T) this;
    }

    public T valueAndRootValue(Object value) {
        this.value(value);
        this.rootValue(value);
        return (T) this;
    }

    public T valueAndRootValue(TaskRunner taskRunner) {
        this.value(taskRunner);
        this.rootValue(taskRunner);
        return (T) this;
    }

    public T valueAndRootValue(Object value, TaskRunner taskRunner) {
        this.value(value, taskRunner);
        this.rootValue(value, taskRunner);
        return (T) this;
    }

    public static Object toValue(Object value){
        Object extractValue = null;
        if (value != null && value instanceof Optional) {
            if (((Optional) value).isPresent()) {
                extractValue = ((Optional) value).get();
            }
        } else {
            extractValue = value;
        }
        return extractValue;
    }

    public static Object toValue(TaskRunner taskRunner){
        if(taskRunner == null) return null;
        Object result = taskRunner.run();
        return toValue(result);
    }

    protected T assertFieldNotEmpty() {
        if (StringUtils.isEmpty(this.field)) {
            throw new RuntimeException("field should not be empty");
        }
        return (T) this;
    }
}