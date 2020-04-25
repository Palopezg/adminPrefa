package ar.com.teco.service;

import ar.com.teco.domain.ServiceType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ServiceType}.
 */
public interface ServiceTypeService {

    /**
     * Save a serviceType.
     *
     * @param serviceType the entity to save.
     * @return the persisted entity.
     */
    ServiceType save(ServiceType serviceType);

    /**
     * Get all the serviceTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServiceType> findAll(Pageable pageable);

    /**
     * Get the "id" serviceType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceType> findOne(Long id);

    /**
     * Delete the "id" serviceType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
