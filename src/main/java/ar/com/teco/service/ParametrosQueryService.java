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

import ar.com.teco.domain.Parametros;
import ar.com.teco.domain.*; // for static metamodels
import ar.com.teco.repository.ParametrosRepository;
import ar.com.teco.service.dto.ParametrosCriteria;

/**
 * Service for executing complex queries for {@link Parametros} entities in the database.
 * The main input is a {@link ParametrosCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Parametros} or a {@link Page} of {@link Parametros} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ParametrosQueryService extends QueryService<Parametros> {

    private final Logger log = LoggerFactory.getLogger(ParametrosQueryService.class);

    private final ParametrosRepository parametrosRepository;

    public ParametrosQueryService(ParametrosRepository parametrosRepository) {
        this.parametrosRepository = parametrosRepository;
    }

    /**
     * Return a {@link List} of {@link Parametros} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Parametros> findByCriteria(ParametrosCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Parametros> specification = createSpecification(criteria);
        return parametrosRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Parametros} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Parametros> findByCriteria(ParametrosCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Parametros> specification = createSpecification(criteria);
        return parametrosRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ParametrosCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Parametros> specification = createSpecification(criteria);
        return parametrosRepository.count(specification);
    }

    /**
     * Function to convert {@link ParametrosCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Parametros> createSpecification(ParametrosCriteria criteria) {
        Specification<Parametros> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Parametros_.id));
            }
            if (criteria.getParametro() != null) {
                specification = specification.and(buildStringSpecification(criteria.getParametro(), Parametros_.parametro));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValor(), Parametros_.valor));
            }
        }
        return specification;
    }
}
