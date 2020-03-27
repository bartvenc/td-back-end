package no.experis.tbbackend.repository;

import java.util.List;

/**
 * The interface Main repository.
 *
 * @param <T> the type parameter
 */
public interface MainRepository<T> {
    /**
     * Save.
     *
     * @param entity the entity
     */
    public void save(T entity);

    /**
     * Update.
     *
     * @param entity the entity
     */
    public void update(T entity);

    /**
     * Find by id t.
     *
     * @param id the id
     * @return the t
     */
    public T findById(int id);

    /**
     * Delete.
     *
     * @param entity the entity
     */
//public List<T> findAllByUserID(int id);
    public void delete(T entity);

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<T> findAll();
}