package ar.com.teco.web.rest;

import ar.com.teco.AdminPrefaApp;
import ar.com.teco.domain.TipoDeco;
import ar.com.teco.repository.TipoDecoRepository;
import ar.com.teco.service.TipoDecoService;
import ar.com.teco.service.dto.TipoDecoCriteria;
import ar.com.teco.service.TipoDecoQueryService;

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

import ar.com.teco.domain.enumeration.Tecnologia;
/**
 * Integration tests for the {@link TipoDecoResource} REST controller.
 */
@SpringBootTest(classes = AdminPrefaApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class TipoDecoResourceIT {

    private static final String DEFAULT_TIPO_DECO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_DECO = "BBBBBBBBBB";

    private static final Tecnologia DEFAULT_TECNOLOGIA = Tecnologia.FTTH;
    private static final Tecnologia UPDATED_TECNOLOGIA = Tecnologia.HFC;

    private static final String DEFAULT_MULTICAST = "AAAAAAAAAA";
    private static final String UPDATED_MULTICAST = "BBBBBBBBBB";

    @Autowired
    private TipoDecoRepository tipoDecoRepository;

    @Autowired
    private TipoDecoService tipoDecoService;

    @Autowired
    private TipoDecoQueryService tipoDecoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoDecoMockMvc;

    private TipoDeco tipoDeco;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoDeco createEntity(EntityManager em) {
        TipoDeco tipoDeco = new TipoDeco()
            .tipoDeco(DEFAULT_TIPO_DECO)
            .tecnologia(DEFAULT_TECNOLOGIA)
            .multicast(DEFAULT_MULTICAST);
        return tipoDeco;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoDeco createUpdatedEntity(EntityManager em) {
        TipoDeco tipoDeco = new TipoDeco()
            .tipoDeco(UPDATED_TIPO_DECO)
            .tecnologia(UPDATED_TECNOLOGIA)
            .multicast(UPDATED_MULTICAST);
        return tipoDeco;
    }

    @BeforeEach
    public void initTest() {
        tipoDeco = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoDeco() throws Exception {
        int databaseSizeBeforeCreate = tipoDecoRepository.findAll().size();

        // Create the TipoDeco
        restTipoDecoMockMvc.perform(post("/api/tipo-decos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeco)))
            .andExpect(status().isCreated());

        // Validate the TipoDeco in the database
        List<TipoDeco> tipoDecoList = tipoDecoRepository.findAll();
        assertThat(tipoDecoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoDeco testTipoDeco = tipoDecoList.get(tipoDecoList.size() - 1);
        assertThat(testTipoDeco.getTipoDeco()).isEqualTo(DEFAULT_TIPO_DECO);
        assertThat(testTipoDeco.getTecnologia()).isEqualTo(DEFAULT_TECNOLOGIA);
        assertThat(testTipoDeco.getMulticast()).isEqualTo(DEFAULT_MULTICAST);
    }

    @Test
    @Transactional
    public void createTipoDecoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoDecoRepository.findAll().size();

        // Create the TipoDeco with an existing ID
        tipoDeco.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoDecoMockMvc.perform(post("/api/tipo-decos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeco)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDeco in the database
        List<TipoDeco> tipoDecoList = tipoDecoRepository.findAll();
        assertThat(tipoDecoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTipoDecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoDecoRepository.findAll().size();
        // set the field null
        tipoDeco.setTipoDeco(null);

        // Create the TipoDeco, which fails.

        restTipoDecoMockMvc.perform(post("/api/tipo-decos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeco)))
            .andExpect(status().isBadRequest());

        List<TipoDeco> tipoDecoList = tipoDecoRepository.findAll();
        assertThat(tipoDecoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTecnologiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoDecoRepository.findAll().size();
        // set the field null
        tipoDeco.setTecnologia(null);

        // Create the TipoDeco, which fails.

        restTipoDecoMockMvc.perform(post("/api/tipo-decos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeco)))
            .andExpect(status().isBadRequest());

        List<TipoDeco> tipoDecoList = tipoDecoRepository.findAll();
        assertThat(tipoDecoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMulticastIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoDecoRepository.findAll().size();
        // set the field null
        tipoDeco.setMulticast(null);

        // Create the TipoDeco, which fails.

        restTipoDecoMockMvc.perform(post("/api/tipo-decos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeco)))
            .andExpect(status().isBadRequest());

        List<TipoDeco> tipoDecoList = tipoDecoRepository.findAll();
        assertThat(tipoDecoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoDecos() throws Exception {
        // Initialize the database
        tipoDecoRepository.saveAndFlush(tipoDeco);

        // Get all the tipoDecoList
        restTipoDecoMockMvc.perform(get("/api/tipo-decos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDeco.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoDeco").value(hasItem(DEFAULT_TIPO_DECO)))
            .andExpect(jsonPath("$.[*].tecnologia").value(hasItem(DEFAULT_TECNOLOGIA.toString())))
            .andExpect(jsonPath("$.[*].multicast").value(hasItem(DEFAULT_MULTICAST)));
    }
    
    @Test
    @Transactional
    public void getTipoDeco() throws Exception {
        // Initialize the database
        tipoDecoRepository.saveAndFlush(tipoDeco);

        // Get the tipoDeco
        restTipoDecoMockMvc.perform(get("/api/tipo-decos/{id}", tipoDeco.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoDeco.getId().intValue()))
            .andExpect(jsonPath("$.tipoDeco").value(DEFAULT_TIPO_DECO))
            .andExpect(jsonPath("$.tecnologia").value(DEFAULT_TECNOLOGIA.toString()))
            .andExpect(jsonPath("$.multicast").value(DEFAULT_MULTICAST));
    }


    @Test
    @Transactional
    public void getTipoDecosByIdFiltering() throws Exception {
        // Initialize the database
        tipoDecoRepository.saveAndFlush(tipoDeco);

        Long id = tipoDeco.getId();

        defaultTipoDecoShouldBeFound("id.equals=" + id);
        defaultTipoDecoShouldNotBeFound("id.notEquals=" + id);

        defaultTipoDecoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTipoDecoShouldNotBeFound("id.greaterThan=" + id);

        defaultTipoDecoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTipoDecoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTipoDecosByTipoDecoIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoDecoRepository.saveAndFlush(tipoDeco);

        // Get all the tipoDecoList where tipoDeco equals to DEFAULT_TIPO_DECO
        defaultTipoDecoShouldBeFound("tipoDeco.equals=" + DEFAULT_TIPO_DECO);

        // Get all the tipoDecoList where tipoDeco equals to UPDATED_TIPO_DECO
        defaultTipoDecoShouldNotBeFound("tipoDeco.equals=" + UPDATED_TIPO_DECO);
    }

    @Test
    @Transactional
    public void getAllTipoDecosByTipoDecoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipoDecoRepository.saveAndFlush(tipoDeco);

        // Get all the tipoDecoList where tipoDeco not equals to DEFAULT_TIPO_DECO
        defaultTipoDecoShouldNotBeFound("tipoDeco.notEquals=" + DEFAULT_TIPO_DECO);

        // Get all the tipoDecoList where tipoDeco not equals to UPDATED_TIPO_DECO
        defaultTipoDecoShouldBeFound("tipoDeco.notEquals=" + UPDATED_TIPO_DECO);
    }

    @Test
    @Transactional
    public void getAllTipoDecosByTipoDecoIsInShouldWork() throws Exception {
        // Initialize the database
        tipoDecoRepository.saveAndFlush(tipoDeco);

        // Get all the tipoDecoList where tipoDeco in DEFAULT_TIPO_DECO or UPDATED_TIPO_DECO
        defaultTipoDecoShouldBeFound("tipoDeco.in=" + DEFAULT_TIPO_DECO + "," + UPDATED_TIPO_DECO);

        // Get all the tipoDecoList where tipoDeco equals to UPDATED_TIPO_DECO
        defaultTipoDecoShouldNotBeFound("tipoDeco.in=" + UPDATED_TIPO_DECO);
    }

    @Test
    @Transactional
    public void getAllTipoDecosByTipoDecoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoDecoRepository.saveAndFlush(tipoDeco);

        // Get all the tipoDecoList where tipoDeco is not null
        defaultTipoDecoShouldBeFound("tipoDeco.specified=true");

        // Get all the tipoDecoList where tipoDeco is null
        defaultTipoDecoShouldNotBeFound("tipoDeco.specified=false");
    }
                @Test
    @Transactional
    public void getAllTipoDecosByTipoDecoContainsSomething() throws Exception {
        // Initialize the database
        tipoDecoRepository.saveAndFlush(tipoDeco);

        // Get all the tipoDecoList where tipoDeco contains DEFAULT_TIPO_DECO
        defaultTipoDecoShouldBeFound("tipoDeco.contains=" + DEFAULT_TIPO_DECO);

        // Get all the tipoDecoList where tipoDeco contains UPDATED_TIPO_DECO
        defaultTipoDecoShouldNotBeFound("tipoDeco.contains=" + UPDATED_TIPO_DECO);
    }

    @Test
    @Transactional
    public void getAllTipoDecosByTipoDecoNotContainsSomething() throws Exception {
        // Initialize the database
        tipoDecoRepository.saveAndFlush(tipoDeco);

        // Get all the tipoDecoList where tipoDeco does not contain DEFAULT_TIPO_DECO
        defaultTipoDecoShouldNotBeFound("tipoDeco.doesNotContain=" + DEFAULT_TIPO_DECO);

        // Get all the tipoDecoList where tipoDeco does not contain UPDATED_TIPO_DECO
        defaultTipoDecoShouldBeFound("tipoDeco.doesNotContain=" + UPDATED_TIPO_DECO);
    }


    @Test
    @Transactional
    public void getAllTipoDecosByTecnologiaIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoDecoRepository.saveAndFlush(tipoDeco);

        // Get all the tipoDecoList where tecnologia equals to DEFAULT_TECNOLOGIA
        defaultTipoDecoShouldBeFound("tecnologia.equals=" + DEFAULT_TECNOLOGIA);

        // Get all the tipoDecoList where tecnologia equals to UPDATED_TECNOLOGIA
        defaultTipoDecoShouldNotBeFound("tecnologia.equals=" + UPDATED_TECNOLOGIA);
    }

    @Test
    @Transactional
    public void getAllTipoDecosByTecnologiaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipoDecoRepository.saveAndFlush(tipoDeco);

        // Get all the tipoDecoList where tecnologia not equals to DEFAULT_TECNOLOGIA
        defaultTipoDecoShouldNotBeFound("tecnologia.notEquals=" + DEFAULT_TECNOLOGIA);

        // Get all the tipoDecoList where tecnologia not equals to UPDATED_TECNOLOGIA
        defaultTipoDecoShouldBeFound("tecnologia.notEquals=" + UPDATED_TECNOLOGIA);
    }

    @Test
    @Transactional
    public void getAllTipoDecosByTecnologiaIsInShouldWork() throws Exception {
        // Initialize the database
        tipoDecoRepository.saveAndFlush(tipoDeco);

        // Get all the tipoDecoList where tecnologia in DEFAULT_TECNOLOGIA or UPDATED_TECNOLOGIA
        defaultTipoDecoShouldBeFound("tecnologia.in=" + DEFAULT_TECNOLOGIA + "," + UPDATED_TECNOLOGIA);

        // Get all the tipoDecoList where tecnologia equals to UPDATED_TECNOLOGIA
        defaultTipoDecoShouldNotBeFound("tecnologia.in=" + UPDATED_TECNOLOGIA);
    }

    @Test
    @Transactional
    public void getAllTipoDecosByTecnologiaIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoDecoRepository.saveAndFlush(tipoDeco);

        // Get all the tipoDecoList where tecnologia is not null
        defaultTipoDecoShouldBeFound("tecnologia.specified=true");

        // Get all the tipoDecoList where tecnologia is null
        defaultTipoDecoShouldNotBeFound("tecnologia.specified=false");
    }

    @Test
    @Transactional
    public void getAllTipoDecosByMulticastIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoDecoRepository.saveAndFlush(tipoDeco);

        // Get all the tipoDecoList where multicast equals to DEFAULT_MULTICAST
        defaultTipoDecoShouldBeFound("multicast.equals=" + DEFAULT_MULTICAST);

        // Get all the tipoDecoList where multicast equals to UPDATED_MULTICAST
        defaultTipoDecoShouldNotBeFound("multicast.equals=" + UPDATED_MULTICAST);
    }

    @Test
    @Transactional
    public void getAllTipoDecosByMulticastIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipoDecoRepository.saveAndFlush(tipoDeco);

        // Get all the tipoDecoList where multicast not equals to DEFAULT_MULTICAST
        defaultTipoDecoShouldNotBeFound("multicast.notEquals=" + DEFAULT_MULTICAST);

        // Get all the tipoDecoList where multicast not equals to UPDATED_MULTICAST
        defaultTipoDecoShouldBeFound("multicast.notEquals=" + UPDATED_MULTICAST);
    }

    @Test
    @Transactional
    public void getAllTipoDecosByMulticastIsInShouldWork() throws Exception {
        // Initialize the database
        tipoDecoRepository.saveAndFlush(tipoDeco);

        // Get all the tipoDecoList where multicast in DEFAULT_MULTICAST or UPDATED_MULTICAST
        defaultTipoDecoShouldBeFound("multicast.in=" + DEFAULT_MULTICAST + "," + UPDATED_MULTICAST);

        // Get all the tipoDecoList where multicast equals to UPDATED_MULTICAST
        defaultTipoDecoShouldNotBeFound("multicast.in=" + UPDATED_MULTICAST);
    }

    @Test
    @Transactional
    public void getAllTipoDecosByMulticastIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoDecoRepository.saveAndFlush(tipoDeco);

        // Get all the tipoDecoList where multicast is not null
        defaultTipoDecoShouldBeFound("multicast.specified=true");

        // Get all the tipoDecoList where multicast is null
        defaultTipoDecoShouldNotBeFound("multicast.specified=false");
    }
                @Test
    @Transactional
    public void getAllTipoDecosByMulticastContainsSomething() throws Exception {
        // Initialize the database
        tipoDecoRepository.saveAndFlush(tipoDeco);

        // Get all the tipoDecoList where multicast contains DEFAULT_MULTICAST
        defaultTipoDecoShouldBeFound("multicast.contains=" + DEFAULT_MULTICAST);

        // Get all the tipoDecoList where multicast contains UPDATED_MULTICAST
        defaultTipoDecoShouldNotBeFound("multicast.contains=" + UPDATED_MULTICAST);
    }

    @Test
    @Transactional
    public void getAllTipoDecosByMulticastNotContainsSomething() throws Exception {
        // Initialize the database
        tipoDecoRepository.saveAndFlush(tipoDeco);

        // Get all the tipoDecoList where multicast does not contain DEFAULT_MULTICAST
        defaultTipoDecoShouldNotBeFound("multicast.doesNotContain=" + DEFAULT_MULTICAST);

        // Get all the tipoDecoList where multicast does not contain UPDATED_MULTICAST
        defaultTipoDecoShouldBeFound("multicast.doesNotContain=" + UPDATED_MULTICAST);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipoDecoShouldBeFound(String filter) throws Exception {
        restTipoDecoMockMvc.perform(get("/api/tipo-decos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDeco.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoDeco").value(hasItem(DEFAULT_TIPO_DECO)))
            .andExpect(jsonPath("$.[*].tecnologia").value(hasItem(DEFAULT_TECNOLOGIA.toString())))
            .andExpect(jsonPath("$.[*].multicast").value(hasItem(DEFAULT_MULTICAST)));

        // Check, that the count call also returns 1
        restTipoDecoMockMvc.perform(get("/api/tipo-decos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipoDecoShouldNotBeFound(String filter) throws Exception {
        restTipoDecoMockMvc.perform(get("/api/tipo-decos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipoDecoMockMvc.perform(get("/api/tipo-decos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTipoDeco() throws Exception {
        // Get the tipoDeco
        restTipoDecoMockMvc.perform(get("/api/tipo-decos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoDeco() throws Exception {
        // Initialize the database
        tipoDecoService.save(tipoDeco);

        int databaseSizeBeforeUpdate = tipoDecoRepository.findAll().size();

        // Update the tipoDeco
        TipoDeco updatedTipoDeco = tipoDecoRepository.findById(tipoDeco.getId()).get();
        // Disconnect from session so that the updates on updatedTipoDeco are not directly saved in db
        em.detach(updatedTipoDeco);
        updatedTipoDeco
            .tipoDeco(UPDATED_TIPO_DECO)
            .tecnologia(UPDATED_TECNOLOGIA)
            .multicast(UPDATED_MULTICAST);

        restTipoDecoMockMvc.perform(put("/api/tipo-decos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoDeco)))
            .andExpect(status().isOk());

        // Validate the TipoDeco in the database
        List<TipoDeco> tipoDecoList = tipoDecoRepository.findAll();
        assertThat(tipoDecoList).hasSize(databaseSizeBeforeUpdate);
        TipoDeco testTipoDeco = tipoDecoList.get(tipoDecoList.size() - 1);
        assertThat(testTipoDeco.getTipoDeco()).isEqualTo(UPDATED_TIPO_DECO);
        assertThat(testTipoDeco.getTecnologia()).isEqualTo(UPDATED_TECNOLOGIA);
        assertThat(testTipoDeco.getMulticast()).isEqualTo(UPDATED_MULTICAST);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoDeco() throws Exception {
        int databaseSizeBeforeUpdate = tipoDecoRepository.findAll().size();

        // Create the TipoDeco

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoDecoMockMvc.perform(put("/api/tipo-decos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeco)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDeco in the database
        List<TipoDeco> tipoDecoList = tipoDecoRepository.findAll();
        assertThat(tipoDecoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoDeco() throws Exception {
        // Initialize the database
        tipoDecoService.save(tipoDeco);

        int databaseSizeBeforeDelete = tipoDecoRepository.findAll().size();

        // Delete the tipoDeco
        restTipoDecoMockMvc.perform(delete("/api/tipo-decos/{id}", tipoDeco.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoDeco> tipoDecoList = tipoDecoRepository.findAll();
        assertThat(tipoDecoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
