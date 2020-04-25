package ar.com.teco.web.rest;

import ar.com.teco.AdminPrefaApp;
import ar.com.teco.domain.Parametros;
import ar.com.teco.repository.ParametrosRepository;
import ar.com.teco.service.ParametrosService;
import ar.com.teco.service.dto.ParametrosCriteria;
import ar.com.teco.service.ParametrosQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ParametrosResource} REST controller.
 */
@SpringBootTest(classes = AdminPrefaApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ParametrosResourceIT {

    private static final String DEFAULT_PARAMETRO = "AAAAAAAAAA";
    private static final String UPDATED_PARAMETRO = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    @Autowired
    private ParametrosRepository parametrosRepository;

    @Autowired
    private ParametrosService parametrosService;

    @Autowired
    private ParametrosQueryService parametrosQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParametrosMockMvc;

    private Parametros parametros;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parametros createEntity(EntityManager em) {
        Parametros parametros = new Parametros()
            .parametro(DEFAULT_PARAMETRO)
            .valor(DEFAULT_VALOR);
        return parametros;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parametros createUpdatedEntity(EntityManager em) {
        Parametros parametros = new Parametros()
            .parametro(UPDATED_PARAMETRO)
            .valor(UPDATED_VALOR);
        return parametros;
    }

    @BeforeEach
    public void initTest() {
        parametros = createEntity(em);
    }

    @Test
    @Transactional
    public void createParametros() throws Exception {
        int databaseSizeBeforeCreate = parametrosRepository.findAll().size();

        // Create the Parametros
        restParametrosMockMvc.perform(post("/api/parametros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(parametros)))
            .andExpect(status().isCreated());

        // Validate the Parametros in the database
        List<Parametros> parametrosList = parametrosRepository.findAll();
        assertThat(parametrosList).hasSize(databaseSizeBeforeCreate + 1);
        Parametros testParametros = parametrosList.get(parametrosList.size() - 1);
        assertThat(testParametros.getParametro()).isEqualTo(DEFAULT_PARAMETRO);
        assertThat(testParametros.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createParametrosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parametrosRepository.findAll().size();

        // Create the Parametros with an existing ID
        parametros.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParametrosMockMvc.perform(post("/api/parametros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(parametros)))
            .andExpect(status().isBadRequest());

        // Validate the Parametros in the database
        List<Parametros> parametrosList = parametrosRepository.findAll();
        assertThat(parametrosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkParametroIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametrosRepository.findAll().size();
        // set the field null
        parametros.setParametro(null);

        // Create the Parametros, which fails.

        restParametrosMockMvc.perform(post("/api/parametros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(parametros)))
            .andExpect(status().isBadRequest());

        List<Parametros> parametrosList = parametrosRepository.findAll();
        assertThat(parametrosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametrosRepository.findAll().size();
        // set the field null
        parametros.setValor(null);

        // Create the Parametros, which fails.

        restParametrosMockMvc.perform(post("/api/parametros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(parametros)))
            .andExpect(status().isBadRequest());

        List<Parametros> parametrosList = parametrosRepository.findAll();
        assertThat(parametrosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParametros() throws Exception {
        // Initialize the database
        parametrosRepository.saveAndFlush(parametros);

        // Get all the parametrosList
        restParametrosMockMvc.perform(get("/api/parametros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parametros.getId().intValue())))
            .andExpect(jsonPath("$.[*].parametro").value(hasItem(DEFAULT_PARAMETRO)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }
    
    @Test
    @Transactional
    public void getParametros() throws Exception {
        // Initialize the database
        parametrosRepository.saveAndFlush(parametros);

        // Get the parametros
        restParametrosMockMvc.perform(get("/api/parametros/{id}", parametros.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parametros.getId().intValue()))
            .andExpect(jsonPath("$.parametro").value(DEFAULT_PARAMETRO))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR));
    }


    @Test
    @Transactional
    public void getParametrosByIdFiltering() throws Exception {
        // Initialize the database
        parametrosRepository.saveAndFlush(parametros);

        Long id = parametros.getId();

        defaultParametrosShouldBeFound("id.equals=" + id);
        defaultParametrosShouldNotBeFound("id.notEquals=" + id);

        defaultParametrosShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultParametrosShouldNotBeFound("id.greaterThan=" + id);

        defaultParametrosShouldBeFound("id.lessThanOrEqual=" + id);
        defaultParametrosShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllParametrosByParametroIsEqualToSomething() throws Exception {
        // Initialize the database
        parametrosRepository.saveAndFlush(parametros);

        // Get all the parametrosList where parametro equals to DEFAULT_PARAMETRO
        defaultParametrosShouldBeFound("parametro.equals=" + DEFAULT_PARAMETRO);

        // Get all the parametrosList where parametro equals to UPDATED_PARAMETRO
        defaultParametrosShouldNotBeFound("parametro.equals=" + UPDATED_PARAMETRO);
    }

    @Test
    @Transactional
    public void getAllParametrosByParametroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parametrosRepository.saveAndFlush(parametros);

        // Get all the parametrosList where parametro not equals to DEFAULT_PARAMETRO
        defaultParametrosShouldNotBeFound("parametro.notEquals=" + DEFAULT_PARAMETRO);

        // Get all the parametrosList where parametro not equals to UPDATED_PARAMETRO
        defaultParametrosShouldBeFound("parametro.notEquals=" + UPDATED_PARAMETRO);
    }

    @Test
    @Transactional
    public void getAllParametrosByParametroIsInShouldWork() throws Exception {
        // Initialize the database
        parametrosRepository.saveAndFlush(parametros);

        // Get all the parametrosList where parametro in DEFAULT_PARAMETRO or UPDATED_PARAMETRO
        defaultParametrosShouldBeFound("parametro.in=" + DEFAULT_PARAMETRO + "," + UPDATED_PARAMETRO);

        // Get all the parametrosList where parametro equals to UPDATED_PARAMETRO
        defaultParametrosShouldNotBeFound("parametro.in=" + UPDATED_PARAMETRO);
    }

    @Test
    @Transactional
    public void getAllParametrosByParametroIsNullOrNotNull() throws Exception {
        // Initialize the database
        parametrosRepository.saveAndFlush(parametros);

        // Get all the parametrosList where parametro is not null
        defaultParametrosShouldBeFound("parametro.specified=true");

        // Get all the parametrosList where parametro is null
        defaultParametrosShouldNotBeFound("parametro.specified=false");
    }
                @Test
    @Transactional
    public void getAllParametrosByParametroContainsSomething() throws Exception {
        // Initialize the database
        parametrosRepository.saveAndFlush(parametros);

        // Get all the parametrosList where parametro contains DEFAULT_PARAMETRO
        defaultParametrosShouldBeFound("parametro.contains=" + DEFAULT_PARAMETRO);

        // Get all the parametrosList where parametro contains UPDATED_PARAMETRO
        defaultParametrosShouldNotBeFound("parametro.contains=" + UPDATED_PARAMETRO);
    }

    @Test
    @Transactional
    public void getAllParametrosByParametroNotContainsSomething() throws Exception {
        // Initialize the database
        parametrosRepository.saveAndFlush(parametros);

        // Get all the parametrosList where parametro does not contain DEFAULT_PARAMETRO
        defaultParametrosShouldNotBeFound("parametro.doesNotContain=" + DEFAULT_PARAMETRO);

        // Get all the parametrosList where parametro does not contain UPDATED_PARAMETRO
        defaultParametrosShouldBeFound("parametro.doesNotContain=" + UPDATED_PARAMETRO);
    }


    @Test
    @Transactional
    public void getAllParametrosByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        parametrosRepository.saveAndFlush(parametros);

        // Get all the parametrosList where valor equals to DEFAULT_VALOR
        defaultParametrosShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the parametrosList where valor equals to UPDATED_VALOR
        defaultParametrosShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllParametrosByValorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parametrosRepository.saveAndFlush(parametros);

        // Get all the parametrosList where valor not equals to DEFAULT_VALOR
        defaultParametrosShouldNotBeFound("valor.notEquals=" + DEFAULT_VALOR);

        // Get all the parametrosList where valor not equals to UPDATED_VALOR
        defaultParametrosShouldBeFound("valor.notEquals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllParametrosByValorIsInShouldWork() throws Exception {
        // Initialize the database
        parametrosRepository.saveAndFlush(parametros);

        // Get all the parametrosList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultParametrosShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the parametrosList where valor equals to UPDATED_VALOR
        defaultParametrosShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllParametrosByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        parametrosRepository.saveAndFlush(parametros);

        // Get all the parametrosList where valor is not null
        defaultParametrosShouldBeFound("valor.specified=true");

        // Get all the parametrosList where valor is null
        defaultParametrosShouldNotBeFound("valor.specified=false");
    }
                @Test
    @Transactional
    public void getAllParametrosByValorContainsSomething() throws Exception {
        // Initialize the database
        parametrosRepository.saveAndFlush(parametros);

        // Get all the parametrosList where valor contains DEFAULT_VALOR
        defaultParametrosShouldBeFound("valor.contains=" + DEFAULT_VALOR);

        // Get all the parametrosList where valor contains UPDATED_VALOR
        defaultParametrosShouldNotBeFound("valor.contains=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllParametrosByValorNotContainsSomething() throws Exception {
        // Initialize the database
        parametrosRepository.saveAndFlush(parametros);

        // Get all the parametrosList where valor does not contain DEFAULT_VALOR
        defaultParametrosShouldNotBeFound("valor.doesNotContain=" + DEFAULT_VALOR);

        // Get all the parametrosList where valor does not contain UPDATED_VALOR
        defaultParametrosShouldBeFound("valor.doesNotContain=" + UPDATED_VALOR);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultParametrosShouldBeFound(String filter) throws Exception {
        restParametrosMockMvc.perform(get("/api/parametros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parametros.getId().intValue())))
            .andExpect(jsonPath("$.[*].parametro").value(hasItem(DEFAULT_PARAMETRO)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));

        // Check, that the count call also returns 1
        restParametrosMockMvc.perform(get("/api/parametros/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultParametrosShouldNotBeFound(String filter) throws Exception {
        restParametrosMockMvc.perform(get("/api/parametros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restParametrosMockMvc.perform(get("/api/parametros/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingParametros() throws Exception {
        // Get the parametros
        restParametrosMockMvc.perform(get("/api/parametros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParametros() throws Exception {
        // Initialize the database
        parametrosService.save(parametros);

        int databaseSizeBeforeUpdate = parametrosRepository.findAll().size();

        // Update the parametros
        Parametros updatedParametros = parametrosRepository.findById(parametros.getId()).get();
        // Disconnect from session so that the updates on updatedParametros are not directly saved in db
        em.detach(updatedParametros);
        updatedParametros
            .parametro(UPDATED_PARAMETRO)
            .valor(UPDATED_VALOR);

        restParametrosMockMvc.perform(put("/api/parametros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedParametros)))
            .andExpect(status().isOk());

        // Validate the Parametros in the database
        List<Parametros> parametrosList = parametrosRepository.findAll();
        assertThat(parametrosList).hasSize(databaseSizeBeforeUpdate);
        Parametros testParametros = parametrosList.get(parametrosList.size() - 1);
        assertThat(testParametros.getParametro()).isEqualTo(UPDATED_PARAMETRO);
        assertThat(testParametros.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingParametros() throws Exception {
        int databaseSizeBeforeUpdate = parametrosRepository.findAll().size();

        // Create the Parametros

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParametrosMockMvc.perform(put("/api/parametros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(parametros)))
            .andExpect(status().isBadRequest());

        // Validate the Parametros in the database
        List<Parametros> parametrosList = parametrosRepository.findAll();
        assertThat(parametrosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParametros() throws Exception {
        // Initialize the database
        parametrosService.save(parametros);

        int databaseSizeBeforeDelete = parametrosRepository.findAll().size();

        // Delete the parametros
        restParametrosMockMvc.perform(delete("/api/parametros/{id}", parametros.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Parametros> parametrosList = parametrosRepository.findAll();
        assertThat(parametrosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
