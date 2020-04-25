package ar.com.teco.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import ar.com.teco.domain.ServiceType;
import ar.com.teco.domain.*; // for static metamodels
import ar.com.teco.repository.ServiceTypeRepository;
import ar.com.teco.service.dto.ServiceTypeCriteria;

/**
 * Service for executing complex queries for {@link ServiceType} entities in the database.
 * The main input is a {@link ServiceTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceType} or a {@link Page} of {@link ServiceType} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceTypeQueryService extends QueryService<ServiceType> {

    private final Logger log = LoggerFactory.getLogger(ServiceTypeQueryService.class);

    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceTypeQueryService(ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
    }

    /**
     * Return a {@link List} of {@link ServiceType} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceType> findByCriteria(ServiceTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceType> specification = createSpecification(criteria);
        return serviceTypeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ServiceType} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceType> findByCriteria(ServiceTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceType> specification = createSpecification(criteria);
        return serviceTypeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceType> specification = createSpecification(criteria);
        return serviceTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link ServiceTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ServiceType> createSpecification(ServiceTypeCriteria criteria) {
        Specification<ServiceType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ServiceType_.id));
            }
            if (criteria.getServiceId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceId(), ServiceType_.serviceId));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), ServiceType_.descripcion));
            }
        }
        return specification;
    }
}
