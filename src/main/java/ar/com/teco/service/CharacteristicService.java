package ar.com.teco.service;

import ar.com.teco.domain.Characteristic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Characteristic}.
 */
public interface CharacteristicService {

    /**
     * Save a characteristic.
     *
     * @param characteristic the entity to save.
     * @return the persisted entity.
     */
    Characteristic save(Characteristic characteristic);

    /**
     * Get all the characteristics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Characteristic> findAll(Pageable pageable);

    /**
     * Get the "id" characteristic.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Characteristic> findOne(Long id);

    /**
     * Delete the "id" characteristic.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
