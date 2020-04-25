package ar.com.teco.service;

import ar.com.teco.domain.TipoDeco;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link TipoDeco}.
 */
public interface TipoDecoService {

    /**
     * Save a tipoDeco.
     *
     * @param tipoDeco the entity to save.
     * @return the persisted entity.
     */
    TipoDeco save(TipoDeco tipoDeco);

    /**
     * Get all the tipoDecos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoDeco> findAll(Pageable pageable);

    /**
     * Get the "id" tipoDeco.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoDeco> findOne(Long id);

    /**
     * Delete the "id" tipoDeco.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
