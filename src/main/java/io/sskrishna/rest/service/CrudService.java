package io.sskrishna.rest.service;

import io.sskrishna.rest.model.JpaModel;
import io.sskrishna.rest.response.RestResponse;
import io.sskrishna.rest.util.IdGenerator;
import io.sskrishna.rest.validation.CrudValidator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.repository.CrudRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class CrudService<T extends JpaModel> {
    private CrudValidator<T> validator;
    private CrudRepository<T, String> repo;

    public CrudService(CrudValidator<T> validator, CrudRepository<T, String> repo) {
        this.validator = validator;
        this.repo = repo;
    }

    public RestResponse create(T entity) {
        this.validator.create(entity);
        this.preCreate(entity);
        if (StringUtils.isEmpty(entity.getId())) {
            entity.setId(IdGenerator.id());
        }
        this.repo.save(entity);
        this.postCreate(entity);
        return RestResponse.init().data(this.repo.findById(entity.getId())).status(201);
    }

    public RestResponse update(T entity) {
        this.validator.update(entity);
        this.preUpdate(entity);
        this.repo.save(entity);
        this.postUpdate(entity);
        return RestResponse.init().data(this.repo.findById(entity.getId())).status(200);
    }

    public RestResponse delete(String id) {
        this.validator.delete(id);
        Optional<T> entityOptional = this.repo.findById(id);
        if (entityOptional.isPresent()) {
            this.preDelete(entityOptional.get());
            this.repo.delete(entityOptional.get());
            this.postDelete(entityOptional.get());
            return RestResponse.init().data(entityOptional.get()).status(200);
        } else {
            return RestResponse.init().status(404);
        }
    }

    public RestResponse findOne(String id) {
        this.validator.findOne(id);
        Optional<T> entity = this.repo.findById(id);
        if (entity.isPresent()) {
            return RestResponse.init().data(entity.get()).status(200);
        } else {
            return RestResponse.init().status(404);
        }
    }

    public RestResponse findAll() {
        List<T> entities = IterableUtils.toList(this.repo.findAll());
        if (CollectionUtils.isEmpty(entities)) {
            return RestResponse.init().data(new LinkedList<>()).status(200);
        } else {
            return RestResponse.init().data(entities).status(200);
        }
    }


    protected void preCreate(T Entity) {

    }

    protected void postCreate(T Entity) {

    }

    protected void preUpdate(T Entity) {

    }

    protected void postUpdate(T Entity) {

    }

    protected void preDelete(T Entity) {

    }

    protected void postDelete(T Entity) {

    }
}
