package io.sskrishna.rest.service;

import io.sskrishna.rest.model.JpaModel;
import io.sskrishna.rest.response.RestResponse;
import io.sskrishna.rest.validation.CrudValidator;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public abstract class JpaCrudService<T extends JpaModel> extends CrudService<T> {
    private CrudValidator<T> validator;
    private JpaRepository<T, String> repo;

    public JpaCrudService(CrudValidator<T> validator, JpaRepository<T, String> repo) {
        super(validator, repo);
        this.validator = validator;
        this.repo = repo;
    }

    @Transactional
    public RestResponse create(T entity) {
        return super.create(entity);
    }

    @Transactional
    public RestResponse update(T entity) {
        return super.update(entity);
    }

    @Transactional
    public RestResponse delete(String id) {
        return super.delete(id);
    }
}
