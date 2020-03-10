package no.experis.tbbackend.Repositories;

import no.experis.tbbackend.Models.User;

import java.util.List;

public interface MainRepository<T> {
    public User save(T entity);
    public void update(T entity);
    public T findById(int id);
    public void delete(T entity);
    public List<T> findAll();
}
