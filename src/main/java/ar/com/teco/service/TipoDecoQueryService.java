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

import ar.com.teco.domain.TipoDeco;
import ar.com.teco.domain.*; // for static metamodels
import ar.com.teco.repository.TipoDecoRepository;
import ar.com.teco.service.dto.TipoDecoCriteria;

/**
 * Service for executing complex queries for {@link TipoDeco} entities in the database.
 * The main input is a {@link TipoDecoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TipoDeco} or a {@link Page} of {@link TipoDeco} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TipoDecoQueryService extends QueryService<TipoDeco> {

    private final Logger log = LoggerFactory.getLogger(TipoDecoQueryService.class);

    private final TipoDecoRepository tipoDecoRepository;

    public TipoDecoQueryService(TipoDecoRepository tipoDecoRepository) {
        this.tipoDecoRepository = tipoDecoRepository;
    }

    /**
     * Return a {@link List} of {@link TipoDeco} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TipoDeco> findByCriteria(TipoDecoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TipoDeco> specification = createSpecification(criteria);
        return tipoDecoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TipoDeco} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoDeco> findByCriteria(TipoDecoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TipoDeco> specification = createSpecification(criteria);
        return tipoDecoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TipoDecoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TipoDeco> specification = createSpecification(criteria);
        return tipoDecoRepository.count(specification);
    }

    /**
     * Function to convert {@link TipoDecoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TipoDeco> createSpecification(TipoDecoCriteria criteria) {
        Specification<TipoDeco> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TipoDeco_.id));
            }
            if (criteria.getTipoDeco() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipoDeco(), TipoDeco_.tipoDeco));
            }
            if (criteria.getTecnologia() != null) {
                specification = specification.and(buildSpecification(criteria.getTecnologia(), TipoDeco_.tecnologia));
            }
            if (criteria.getMulticast() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMulticast(), TipoDeco_.multicast));
            }
        }
        return specification;
    }
}
