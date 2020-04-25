package ar.com.teco.service.impl;

import ar.com.teco.service.ServiceTypeService;
import ar.com.teco.domain.ServiceType;
import ar.com.teco.repository.ServiceTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ServiceType}.
 */
@Service
@Transactional
public class ServiceTypeServiceImpl implements ServiceTypeService {

    private final Logger log = LoggerFactory.getLogger(ServiceTypeServiceImpl.class);

    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceTypeServiceImpl(ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
    }

    /**
     * Save a serviceType.
     *
     * @param serviceType the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ServiceType save(ServiceType serviceType) {
        log.debug("Request to save ServiceType : {}", serviceType);
        return serviceTypeRepository.save(serviceType);
    }

    /**
     * Get all the serviceTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceType> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceTypes");
        return serviceTypeRepository.findAll(pageable);
    }

    /**
     * Get one serviceType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceType> findOne(Long id) {
        log.debug("Request to get ServiceType : {}", id);
        return serviceTypeRepository.findById(id);
    }

    /**
     * Delete the serviceType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceType : {}", id);
        serviceTypeRepository.deleteById(id);
    }
}
