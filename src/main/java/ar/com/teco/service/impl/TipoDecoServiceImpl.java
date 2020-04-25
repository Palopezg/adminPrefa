package ar.com.teco.service.impl;

import ar.com.teco.service.TipoDecoService;
import ar.com.teco.domain.TipoDeco;
import ar.com.teco.repository.TipoDecoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TipoDeco}.
 */
@Service
@Transactional
public class TipoDecoServiceImpl implements TipoDecoService {

    private final Logger log = LoggerFactory.getLogger(TipoDecoServiceImpl.class);

    private final TipoDecoRepository tipoDecoRepository;

    public TipoDecoServiceImpl(TipoDecoRepository tipoDecoRepository) {
        this.tipoDecoRepository = tipoDecoRepository;
    }

    /**
     * Save a tipoDeco.
     *
     * @param tipoDeco the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TipoDeco save(TipoDeco tipoDeco) {
        log.debug("Request to save TipoDeco : {}", tipoDeco);
        return tipoDecoRepository.save(tipoDeco);
    }

    /**
     * Get all the tipoDecos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoDeco> findAll(Pageable pageable) {
        log.debug("Request to get all TipoDecos");
        return tipoDecoRepository.findAll(pageable);
    }

    /**
     * Get one tipoDeco by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoDeco> findOne(Long id) {
        log.debug("Request to get TipoDeco : {}", id);
        return tipoDecoRepository.findById(id);
    }

    /**
     * Delete the tipoDeco by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoDeco : {}", id);
        tipoDecoRepository.deleteById(id);
    }
}
