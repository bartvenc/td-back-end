package no.experis.tbbackend.repository;

import java.util.List;

public interface MainRepository<T> {
    public void save(T entity);

    public void update(T entity);

    public T findById(int id);

    public void delete(T entity);

    public List<T> findAll();
}