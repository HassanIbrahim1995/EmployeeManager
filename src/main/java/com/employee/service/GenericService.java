package com.employee.service;

/**
 * The GenericService interface provides common CRUD operations for entities.
 * @param <T> the type of entity
 */

public interface GenericService<T> {

    /**
     * Retrieves an entity by its ID.
     *
     * @param id the ID of the entity to retrieve
     * @return an Optional containing the retrieved entity, or an empty Optional if not found
     */
    T getById(Long id);

    /**
     * Saves an entity.
     *
     * @param entity the entity to save
     * @return the saved entity
     */
    T save(T entity);

    /**
     * Deletes an entity.
     *
     * @param entity the entity to delete
     */
    void delete(T entity);

    /**
     * Updates an entity.
     *
     * @param entity the entity to update
     * @return the updated entity
     */
    T update(T entity);
}
