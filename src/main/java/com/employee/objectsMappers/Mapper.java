package com.employee.objectsMappers;

import org.springframework.stereotype.Component;

/**
 * A generic mapper interface for mapping between source and target objects.
 * @param <S> the source type
 * @param <T> the target type
 */
@Component
public interface Mapper<S, T> {
    /**
     * Maps the source object to the target object.
     * @param entity the source object
     * @return the target object
     */
    T mapToDTO(S entity);
    /**
     * Maps the target object to the source object.
     * @param dto the target object
     * @return the source object
     */

    S mapFromDTO(T dto);
}
