package io.sskrishna.rest.web;

import io.sskrishna.rest.model.JpaModel;
import io.sskrishna.rest.response.RestResponse;
import io.sskrishna.rest.service.CrudService;
import io.sskrishna.rest.validation.CrudValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class CrudCtrl<T extends JpaModel> {
    private CrudService<T> service;
    private CrudValidator<T> validator;

    public CrudCtrl(CrudValidator validator, CrudService<T> service) {
        this.validator = validator;
        this.service = service;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody T entity) {
        this.validator.create(entity);
        RestResponse restResponse = this.service.create(entity);
        return restResponse.responseEntity();
    }

    @PutMapping
    public ResponseEntity update(@RequestBody T entity) {
        this.validator.update(entity);
        RestResponse restResponse = this.service.update(entity);
        return restResponse.responseEntity();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        this.validator.delete(id);
        RestResponse restResponse = this.service.delete(id);
        return restResponse.responseEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity findOne(@PathVariable("id") String id) {
        this.validator.findOne(id);
        RestResponse restResponse = this.service.findOne(id);
        return restResponse.responseEntity();
    }

    @GetMapping
    public RestResponse findAll() {
        RestResponse restResponse = this.service.findAll();
        return restResponse;
    }
}