package ar.com.teco.service.impl;

import ar.com.teco.service.ParametrosService;
import ar.com.teco.domain.Parametros;
import ar.com.teco.repository.ParametrosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Parametros}.
 */
@Service
@Transactional
public class ParametrosServiceImpl implements ParametrosService {

    private final Logger log = LoggerFactory.getLogger(ParametrosServiceImpl.class);

    private final ParametrosRepository parametrosRepository;

    public ParametrosServiceImpl(ParametrosRepository parametrosRepository) {
        this.parametrosRepository = parametrosRepository;
    }

    /**
     * Save a parametros.
     *
     * @param parametros the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Parametros save(Parametros parametros) {
        log.debug("Request to save Parametros : {}", parametros);
        return parametrosRepository.save(parametros);
    }

    /**
     * Get all the parametros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Parametros> findAll(Pageable pageable) {
        log.debug("Request to get all Parametros");
        return parametrosRepository.findAll(pageable);
    }

    /**
     * Get one parametros by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Parametros> findOne(Long id) {
        log.debug("Request to get Parametros : {}", id);
        return parametrosRepository.findById(id);
    }

    /**
     * Delete the parametros by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Parametros : {}", id);
        parametrosRepository.deleteById(id);
    }
}
