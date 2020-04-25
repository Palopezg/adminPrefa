package ar.com.teco.web.rest;

import ar.com.teco.domain.TipoDeco;
import ar.com.teco.service.TipoDecoService;
import ar.com.teco.web.rest.errors.BadRequestAlertException;
import ar.com.teco.service.dto.TipoDecoCriteria;
import ar.com.teco.service.TipoDecoQueryService;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ar.com.teco.domain.TipoDeco}.
 */
@RestController
@RequestMapping("/api")
public class TipoDecoResource {

    private final Logger log = LoggerFactory.getLogger(TipoDecoResource.class);

    private static final String ENTITY_NAME = "tipoDeco";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoDecoService tipoDecoService;

    private final TipoDecoQueryService tipoDecoQueryService;

    public TipoDecoResource(TipoDecoService tipoDecoService, TipoDecoQueryService tipoDecoQueryService) {
        this.tipoDecoService = tipoDecoService;
        this.tipoDecoQueryService = tipoDecoQueryService;
    }

    /**
     * {@code POST  /tipo-decos} : Create a new tipoDeco.
     *
     * @param tipoDeco the tipoDeco to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoDeco, or with status {@code 400 (Bad Request)} if the tipoDeco has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-decos")
    public ResponseEntity<TipoDeco> createTipoDeco(@Valid @RequestBody TipoDeco tipoDeco) throws URISyntaxException {
        log.debug("REST request to save TipoDeco : {}", tipoDeco);
        if (tipoDeco.getId() != null) {
            throw new BadRequestAlertException("A new tipoDeco cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoDeco result = tipoDecoService.save(tipoDeco);
        return ResponseEntity.created(new URI("/api/tipo-decos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-decos} : Updates an existing tipoDeco.
     *
     * @param tipoDeco the tipoDeco to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoDeco,
     * or with status {@code 400 (Bad Request)} if the tipoDeco is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoDeco couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-decos")
    public ResponseEntity<TipoDeco> updateTipoDeco(@Valid @RequestBody TipoDeco tipoDeco) throws URISyntaxException {
        log.debug("REST request to update TipoDeco : {}", tipoDeco);
        if (tipoDeco.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoDeco result = tipoDecoService.save(tipoDeco);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoDeco.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-decos} : get all the tipoDecos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoDecos in body.
     */
    @GetMapping("/tipo-decos")
    public ResponseEntity<List<TipoDeco>> getAllTipoDecos(TipoDecoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TipoDecos by criteria: {}", criteria);
        Page<TipoDeco> page = tipoDecoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-decos/count} : count all the tipoDecos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tipo-decos/count")
    public ResponseEntity<Long> countTipoDecos(TipoDecoCriteria criteria) {
        log.debug("REST request to count TipoDecos by criteria: {}", criteria);
        return ResponseEntity.ok().body(tipoDecoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tipo-decos/:id} : get the "id" tipoDeco.
     *
     * @param id the id of the tipoDeco to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoDeco, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-decos/{id}")
    public ResponseEntity<TipoDeco> getTipoDeco(@PathVariable Long id) {
        log.debug("REST request to get TipoDeco : {}", id);
        Optional<TipoDeco> tipoDeco = tipoDecoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoDeco);
    }

    /**
     * {@code DELETE  /tipo-decos/:id} : delete the "id" tipoDeco.
     *
     * @param id the id of the tipoDeco to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-decos/{id}")
    public ResponseEntity<Void> deleteTipoDeco(@PathVariable Long id) {
        log.debug("REST request to delete TipoDeco : {}", id);
        tipoDecoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
