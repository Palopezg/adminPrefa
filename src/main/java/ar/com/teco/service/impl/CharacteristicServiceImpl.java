package ar.com.teco.service.impl;

import ar.com.teco.service.CharacteristicService;
import ar.com.teco.domain.Characteristic;
import ar.com.teco.repository.CharacteristicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Characteristic}.
 */
@Service
@Transactional
public class CharacteristicServiceImpl implements CharacteristicService {

    private final Logger log = LoggerFactory.getLogger(CharacteristicServiceImpl.class);

    private final CharacteristicRepository characteristicRepository;

    public CharacteristicServiceImpl(CharacteristicRepository characteristicRepository) {
        this.characteristicRepository = characteristicRepository;
    }

    /**
     * Save a characteristic.
     *
     * @param characteristic the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Characteristic save(Characteristic characteristic) {
        log.debug("Request to save Characteristic : {}", characteristic);
        return characteristicRepository.save(characteristic);
    }

    /**
     * Get all the characteristics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Characteristic> findAll(Pageable pageable) {
        log.debug("Request to get all Characteristics");
        return characteristicRepository.findAll(pageable);
    }

    /**
     * Get one characteristic by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Characteristic> findOne(Long id) {
        log.debug("Request to get Characteristic : {}", id);
        return characteristicRepository.findById(id);
    }

    /**
     * Delete the characteristic by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Characteristic : {}", id);
        characteristicRepository.deleteById(id);
    }
}
