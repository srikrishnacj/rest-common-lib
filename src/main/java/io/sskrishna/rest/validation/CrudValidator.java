package io.sskrishna.rest.validation;

import io.sskrishna.rest.config.ApplicationContextHolder;
import io.sskrishna.rest.exception.container.FormError;
import io.sskrishna.rest.exception.container.MultiRestError;
import io.sskrishna.rest.exception.container.RestError;
import io.sskrishna.rest.exception.container.RestErrorBuilder;
import io.sskrishna.rest.model.JpaModel;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public interface CrudValidator<T extends JpaModel> {
    void create(T entity);

    void update(T entity);

    void delete(String id);

    void findOne(String id);

    default RestError restError() {
        ApplicationContext context = ApplicationContextHolder.getApplicationContext();
        RestErrorBuilder builder = context.getBean(RestErrorBuilder.class);
        return builder.restError();
    }

    default MultiRestError multiRestError() {
        ApplicationContext context = ApplicationContextHolder.getApplicationContext();
        RestErrorBuilder builder = context.getBean(RestErrorBuilder.class);
        return builder.multiRestError();
    }

    default FormError formError() {
        ApplicationContext context = ApplicationContextHolder.getApplicationContext();
        RestErrorBuilder builder = context.getBean(RestErrorBuilder.class);
        return builder.formError();
    }
}