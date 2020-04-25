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

import ar.com.teco.domain.Characteristic;
import ar.com.teco.domain.*; // for static metamodels
import ar.com.teco.repository.CharacteristicRepository;
import ar.com.teco.service.dto.CharacteristicCriteria;

/**
 * Service for executing complex queries for {@link Characteristic} entities in the database.
 * The main input is a {@link CharacteristicCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Characteristic} or a {@link Page} of {@link Characteristic} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CharacteristicQueryService extends QueryService<Characteristic> {

    private final Logger log = LoggerFactory.getLogger(CharacteristicQueryService.class);

    private final CharacteristicRepository characteristicRepository;

    public CharacteristicQueryService(CharacteristicRepository characteristicRepository) {
        this.characteristicRepository = characteristicRepository;
    }

    /**
     * Return a {@link List} of {@link Characteristic} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Characteristic> findByCriteria(CharacteristicCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Characteristic> specification = createSpecification(criteria);
        return characteristicRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Characteristic} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Characteristic> findByCriteria(CharacteristicCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Characteristic> specification = createSpecification(criteria);
        return characteristicRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CharacteristicCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Characteristic> specification = createSpecification(criteria);
        return characteristicRepository.count(specification);
    }

    /**
     * Function to convert {@link CharacteristicCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Characteristic> createSpecification(CharacteristicCriteria criteria) {
        Specification<Characteristic> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Characteristic_.id));
            }
            if (criteria.getCharacteristicId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCharacteristicId(), Characteristic_.characteristicId));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), Characteristic_.descripcion));
            }
            if (criteria.getServiceTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceTypeId(),
                    root -> root.join(Characteristic_.serviceType, JoinType.LEFT).get(ServiceType_.id)));
            }
        }
        return specification;
    }
}
