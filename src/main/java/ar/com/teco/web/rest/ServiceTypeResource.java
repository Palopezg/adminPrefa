package ar.com.teco.web.rest;

import ar.com.teco.domain.ServiceType;
import ar.com.teco.service.ServiceTypeService;
import ar.com.teco.web.rest.errors.BadRequestAlertException;
import ar.com.teco.service.dto.ServiceTypeCriteria;
import ar.com.teco.service.ServiceTypeQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ar.com.teco.domain.ServiceType}.
 */
@RestController
@RequestMapping("/api")
public class ServiceTypeResource {

    private final Logger log = LoggerFactory.getLogger(ServiceTypeResource.class);

    private static final String ENTITY_NAME = "serviceType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceTypeService serviceTypeService;

    private final ServiceTypeQueryService serviceTypeQueryService;

    public ServiceTypeResource(ServiceTypeService serviceTypeService, ServiceTypeQueryService serviceTypeQueryService) {
        this.serviceTypeService = serviceTypeService;
        this.serviceTypeQueryService = serviceTypeQueryService;
    }

    /**
     * {@code POST  /service-types} : Create a new serviceType.
     *
     * @param serviceType the serviceType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceType, or with status {@code 400 (Bad Request)} if the serviceType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-types")
    public ResponseEntity<ServiceType> createServiceType(@RequestBody ServiceType serviceType) throws URISyntaxException {
        log.debug("REST request to save ServiceType : {}", serviceType);
        if (serviceType.getId() != null) {
            throw new BadRequestAlertException("A new serviceType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceType result = serviceTypeService.save(serviceType);
        return ResponseEntity.created(new URI("/api/service-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-types} : Updates an existing serviceType.
     *
     * @param serviceType the serviceType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceType,
     * or with status {@code 400 (Bad Request)} if the serviceType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-types")
    public ResponseEntity<ServiceType> updateServiceType(@RequestBody ServiceType serviceType) throws URISyntaxException {
        log.debug("REST request to update ServiceType : {}", serviceType);
        if (serviceType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceType result = serviceTypeService.save(serviceType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, serviceType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-types} : get all the serviceTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceTypes in body.
     */
    @GetMapping("/service-types")
    public ResponseEntity<List<ServiceType>> getAllServiceTypes(ServiceTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ServiceTypes by criteria: {}", criteria);
        Page<ServiceType> page = serviceTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /service-types/count} : count all the serviceTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/service-types/count")
    public ResponseEntity<Long> countServiceTypes(ServiceTypeCriteria criteria) {
        log.debug("REST request to count ServiceTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-types/:id} : get the "id" serviceType.
     *
     * @param id the id of the serviceType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-types/{id}")
    public ResponseEntity<ServiceType> getServiceType(@PathVariable Long id) {
        log.debug("REST request to get ServiceType : {}", id);
        Optional<ServiceType> serviceType = serviceTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceType);
    }

    /**
     * {@code DELETE  /service-types/:id} : delete the "id" serviceType.
     *
     * @param id the id of the serviceType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-types/{id}")
    public ResponseEntity<Void> deleteServiceType(@PathVariable Long id) {
        log.debug("REST request to delete ServiceType : {}", id);
        serviceTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
