package com.restaurant.service.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public abstract class BaseServiceImp<T, R> implements BaseService<T, R>  {

    @Autowired
    private JpaRepository<T, R> repository;

    @Override
    public T save(T model) throws Exception {
        return repository.save(model);
    }

    @Override
    public void delete(R id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public void update(T model) throws Exception {
        repository.save(model);
    }

    @Override
    public Optional<T> findById(R id) throws Exception {
        return repository.findById(id);
    }

    @Override
    public List<T> findAll() throws Exception {
        return repository.findAll();
    }

    @Override
    public Page<T> findAllByPage(int pageNum, int limit) throws Exception {
        if (limit == 0) {
            limit = DEFAULT_PAGE_SIZE;
        }
        Pageable pageable = new PageRequest(pageNum, limit);
        return repository.findAll(pageable);
    }
}
