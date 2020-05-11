package io.sskrishna.rest.exception.container.validations;

import io.sskrishna.rest.exception.container.FormError;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.InetAddressValidator;

public class StringFieldValidator extends AbstractFieldValidator<StringFieldValidator> {
    public StringFieldValidator(FormError formError) {
        super(formError);
    }

    public StringFieldValidator(AbstractFieldValidator validator) {
        super(validator);
    }

    @Override
    protected StringFieldValidator self() {
        return this;
    }

    private String valueAsString() {
        Object value = super.value();

        if (value == null) return null;

        if (value instanceof String) {
            return value.toString();
        }
        throw new RuntimeException("Value is not a String object");
    }

    public StringFieldValidator rejectIfEmpty(String code) {
        if (super.shouldSkipRejection()) return self();
        String value = this.valueAsString();

        if (StringUtils.isEmpty(value)) {
            super.rejectField(code);
        }

        return self();
    }

    public StringFieldValidator rejectIfNotEmpty(String code) {
        if (super.shouldSkipRejection()) return self();
        String value = this.valueAsString();

        if (StringUtils.isEmpty(value) == false) {
            super.rejectField(code);
        }

        return self();
    }

    public StringFieldValidator rejectIfNotAlphanumeric(String code) {
        if (super.shouldSkipRejection()) return self();
        String value = this.valueAsString();

        if (StringUtils.isAlphanumeric(value) == false) {
            super.rejectField(code);
        }
        return self();
    }

    public StringFieldValidator rejectIfNotAlphanumericSpace(String code) {
        if (super.shouldSkipRejection()) return self();
        String value = this.valueAsString();

        if (StringUtils.isAlphanumericSpace(value) == false) {
            super.rejectField(code);
        }
        return self();
    }

    public StringFieldValidator rejectIfMaxLengthExceeds(String code, int maxLength) {
        if (super.shouldSkipRejection()) return self();
        String value = this.valueAsString();
        if (value == null) return self();

        if (value.length() > maxLength) {
            super.rejectField(code);
        }
        return self();
    }

    public StringFieldValidator rejectIfMinLengthBelow(int minLength, String code) {
        if (super.shouldSkipRejection()) return self();
        String value = this.valueAsString();
        if (value == null) return self();

        if (value.length() < minLength) {
            super.rejectField(code);
        }
        return self();
    }

    public StringFieldValidator rejectIfLengthNotBetween(String code, int minLength, int maxLength) {
        if (super.shouldSkipRejection()) return self();
        String value = this.valueAsString();
        if (value == null) return self();

        if (value.length() < minLength || value.length() > maxLength) {
            super.rejectField(code);
        }
        return self();
    }

    public StringFieldValidator rejectIfLengthNotEquals(String code, int length) {
        if (super.shouldSkipRejection()) return self();
        String value = this.valueAsString();
        if (value == null) return self();

        if (value.length() != length) {
            super.rejectField(code);
        }
        return self();
    }

    public StringFieldValidator rejectIfEquals(String code, Object otherValue) {
        if (super.shouldSkipRejection()) return self();
        String value = this.valueAsString();

        if (otherValue == null || value == null) return self();
        if (otherValue.equals(value)) {
            super.rejectField(code);
        }
        return self();
    }

    public StringFieldValidator rejectIfNotEquals(String code, Object otherValue) {
        if (super.shouldSkipRejection()) return self();
        String value = this.valueAsString();

        if (otherValue == null || value == null) {
            return self();
        } else if (otherValue.equals(value) == false) {
            super.rejectField(code);
        }
        return self();
    }

    public StringFieldValidator rejectIfNotNullAndNotEquals(String code, TaskRunner taskRunner) {
        if (super.shouldSkipRejection()) return self();
        String value = this.valueAsString();

        Object valueObj = taskRunner.run();
        if (valueObj != null && valueObj.equals(value) == false) {
            super.rejectField(code);
        }
        return self();
    }

    public StringFieldValidator rejectIfNotHostAddress(String code) {
        if (super.shouldSkipRejection()) return self();
        String value = this.valueAsString();
        if (DomainValidator.getInstance().isValid(value) || InetAddressValidator.getInstance().isValid(value)) {
            return self();
        } else {
            super.rejectField(code);
            return self();
        }
    }

}
