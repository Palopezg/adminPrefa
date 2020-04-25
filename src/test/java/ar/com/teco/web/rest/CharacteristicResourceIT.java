package ar.com.teco.web.rest;

import ar.com.teco.AdminPrefaApp;
import ar.com.teco.domain.Characteristic;
import ar.com.teco.domain.ServiceType;
import ar.com.teco.repository.CharacteristicRepository;
import ar.com.teco.service.CharacteristicService;
import ar.com.teco.service.dto.CharacteristicCriteria;
import ar.com.teco.service.CharacteristicQueryService;

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
 * Integration tests for the {@link CharacteristicResource} REST controller.
 */
@SpringBootTest(classes = AdminPrefaApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CharacteristicResourceIT {

    private static final String DEFAULT_CHARACTERISTIC_ID = "AAAAAAAAAA";
    private static final String UPDATED_CHARACTERISTIC_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private CharacteristicRepository characteristicRepository;

    @Autowired
    private CharacteristicService characteristicService;

    @Autowired
    private CharacteristicQueryService characteristicQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCharacteristicMockMvc;

    private Characteristic characteristic;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Characteristic createEntity(EntityManager em) {
        Characteristic characteristic = new Characteristic()
            .characteristicId(DEFAULT_CHARACTERISTIC_ID)
            .descripcion(DEFAULT_DESCRIPCION);
        return characteristic;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Characteristic createUpdatedEntity(EntityManager em) {
        Characteristic characteristic = new Characteristic()
            .characteristicId(UPDATED_CHARACTERISTIC_ID)
            .descripcion(UPDATED_DESCRIPCION);
        return characteristic;
    }

    @BeforeEach
    public void initTest() {
        characteristic = createEntity(em);
    }

    @Test
    @Transactional
    public void createCharacteristic() throws Exception {
        int databaseSizeBeforeCreate = characteristicRepository.findAll().size();

        // Create the Characteristic
        restCharacteristicMockMvc.perform(post("/api/characteristics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(characteristic)))
            .andExpect(status().isCreated());

        // Validate the Characteristic in the database
        List<Characteristic> characteristicList = characteristicRepository.findAll();
        assertThat(characteristicList).hasSize(databaseSizeBeforeCreate + 1);
        Characteristic testCharacteristic = characteristicList.get(characteristicList.size() - 1);
        assertThat(testCharacteristic.getCharacteristicId()).isEqualTo(DEFAULT_CHARACTERISTIC_ID);
        assertThat(testCharacteristic.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createCharacteristicWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = characteristicRepository.findAll().size();

        // Create the Characteristic with an existing ID
        characteristic.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCharacteristicMockMvc.perform(post("/api/characteristics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(characteristic)))
            .andExpect(status().isBadRequest());

        // Validate the Characteristic in the database
        List<Characteristic> characteristicList = characteristicRepository.findAll();
        assertThat(characteristicList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCharacteristicIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = characteristicRepository.findAll().size();
        // set the field null
        characteristic.setCharacteristicId(null);

        // Create the Characteristic, which fails.

        restCharacteristicMockMvc.perform(post("/api/characteristics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(characteristic)))
            .andExpect(status().isBadRequest());

        List<Characteristic> characteristicList = characteristicRepository.findAll();
        assertThat(characteristicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCharacteristics() throws Exception {
        // Initialize the database
        characteristicRepository.saveAndFlush(characteristic);

        // Get all the characteristicList
        restCharacteristicMockMvc.perform(get("/api/characteristics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(characteristic.getId().intValue())))
            .andExpect(jsonPath("$.[*].characteristicId").value(hasItem(DEFAULT_CHARACTERISTIC_ID)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }
    
    @Test
    @Transactional
    public void getCharacteristic() throws Exception {
        // Initialize the database
        characteristicRepository.saveAndFlush(characteristic);

        // Get the characteristic
        restCharacteristicMockMvc.perform(get("/api/characteristics/{id}", characteristic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(characteristic.getId().intValue()))
            .andExpect(jsonPath("$.characteristicId").value(DEFAULT_CHARACTERISTIC_ID))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }


    @Test
    @Transactional
    public void getCharacteristicsByIdFiltering() throws Exception {
        // Initialize the database
        characteristicRepository.saveAndFlush(characteristic);

        Long id = characteristic.getId();

        defaultCharacteristicShouldBeFound("id.equals=" + id);
        defaultCharacteristicShouldNotBeFound("id.notEquals=" + id);

        defaultCharacteristicShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCharacteristicShouldNotBeFound("id.greaterThan=" + id);

        defaultCharacteristicShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCharacteristicShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCharacteristicsByCharacteristicIdIsEqualToSomething() throws Exception {
        // Initialize the database
        characteristicRepository.saveAndFlush(characteristic);

        // Get all the characteristicList where characteristicId equals to DEFAULT_CHARACTERISTIC_ID
        defaultCharacteristicShouldBeFound("characteristicId.equals=" + DEFAULT_CHARACTERISTIC_ID);

        // Get all the characteristicList where characteristicId equals to UPDATED_CHARACTERISTIC_ID
        defaultCharacteristicShouldNotBeFound("characteristicId.equals=" + UPDATED_CHARACTERISTIC_ID);
    }

    @Test
    @Transactional
    public void getAllCharacteristicsByCharacteristicIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        characteristicRepository.saveAndFlush(characteristic);

        // Get all the characteristicList where characteristicId not equals to DEFAULT_CHARACTERISTIC_ID
        defaultCharacteristicShouldNotBeFound("characteristicId.notEquals=" + DEFAULT_CHARACTERISTIC_ID);

        // Get all the characteristicList where characteristicId not equals to UPDATED_CHARACTERISTIC_ID
        defaultCharacteristicShouldBeFound("characteristicId.notEquals=" + UPDATED_CHARACTERISTIC_ID);
    }

    @Test
    @Transactional
    public void getAllCharacteristicsByCharacteristicIdIsInShouldWork() throws Exception {
        // Initialize the database
        characteristicRepository.saveAndFlush(characteristic);

        // Get all the characteristicList where characteristicId in DEFAULT_CHARACTERISTIC_ID or UPDATED_CHARACTERISTIC_ID
        defaultCharacteristicShouldBeFound("characteristicId.in=" + DEFAULT_CHARACTERISTIC_ID + "," + UPDATED_CHARACTERISTIC_ID);

        // Get all the characteristicList where characteristicId equals to UPDATED_CHARACTERISTIC_ID
        defaultCharacteristicShouldNotBeFound("characteristicId.in=" + UPDATED_CHARACTERISTIC_ID);
    }

    @Test
    @Transactional
    public void getAllCharacteristicsByCharacteristicIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        characteristicRepository.saveAndFlush(characteristic);

        // Get all the characteristicList where characteristicId is not null
        defaultCharacteristicShouldBeFound("characteristicId.specified=true");

        // Get all the characteristicList where characteristicId is null
        defaultCharacteristicShouldNotBeFound("characteristicId.specified=false");
    }
                @Test
    @Transactional
    public void getAllCharacteristicsByCharacteristicIdContainsSomething() throws Exception {
        // Initialize the database
        characteristicRepository.saveAndFlush(characteristic);

        // Get all the characteristicList where characteristicId contains DEFAULT_CHARACTERISTIC_ID
        defaultCharacteristicShouldBeFound("characteristicId.contains=" + DEFAULT_CHARACTERISTIC_ID);

        // Get all the characteristicList where characteristicId contains UPDATED_CHARACTERISTIC_ID
        defaultCharacteristicShouldNotBeFound("characteristicId.contains=" + UPDATED_CHARACTERISTIC_ID);
    }

    @Test
    @Transactional
    public void getAllCharacteristicsByCharacteristicIdNotContainsSomething() throws Exception {
        // Initialize the database
        characteristicRepository.saveAndFlush(characteristic);

        // Get all the characteristicList where characteristicId does not contain DEFAULT_CHARACTERISTIC_ID
        defaultCharacteristicShouldNotBeFound("characteristicId.doesNotContain=" + DEFAULT_CHARACTERISTIC_ID);

        // Get all the characteristicList where characteristicId does not contain UPDATED_CHARACTERISTIC_ID
        defaultCharacteristicShouldBeFound("characteristicId.doesNotContain=" + UPDATED_CHARACTERISTIC_ID);
    }


    @Test
    @Transactional
    public void getAllCharacteristicsByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        characteristicRepository.saveAndFlush(characteristic);

        // Get all the characteristicList where descripcion equals to DEFAULT_DESCRIPCION
        defaultCharacteristicShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the characteristicList where descripcion equals to UPDATED_DESCRIPCION
        defaultCharacteristicShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllCharacteristicsByDescripcionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        characteristicRepository.saveAndFlush(characteristic);

        // Get all the characteristicList where descripcion not equals to DEFAULT_DESCRIPCION
        defaultCharacteristicShouldNotBeFound("descripcion.notEquals=" + DEFAULT_DESCRIPCION);

        // Get all the characteristicList where descripcion not equals to UPDATED_DESCRIPCION
        defaultCharacteristicShouldBeFound("descripcion.notEquals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllCharacteristicsByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        characteristicRepository.saveAndFlush(characteristic);

        // Get all the characteristicList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultCharacteristicShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the characteristicList where descripcion equals to UPDATED_DESCRIPCION
        defaultCharacteristicShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllCharacteristicsByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        characteristicRepository.saveAndFlush(characteristic);

        // Get all the characteristicList where descripcion is not null
        defaultCharacteristicShouldBeFound("descripcion.specified=true");

        // Get all the characteristicList where descripcion is null
        defaultCharacteristicShouldNotBeFound("descripcion.specified=false");
    }
                @Test
    @Transactional
    public void getAllCharacteristicsByDescripcionContainsSomething() throws Exception {
        // Initialize the database
        characteristicRepository.saveAndFlush(characteristic);

        // Get all the characteristicList where descripcion contains DEFAULT_DESCRIPCION
        defaultCharacteristicShouldBeFound("descripcion.contains=" + DEFAULT_DESCRIPCION);

        // Get all the characteristicList where descripcion contains UPDATED_DESCRIPCION
        defaultCharacteristicShouldNotBeFound("descripcion.contains=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllCharacteristicsByDescripcionNotContainsSomething() throws Exception {
        // Initialize the database
        characteristicRepository.saveAndFlush(characteristic);

        // Get all the characteristicList where descripcion does not contain DEFAULT_DESCRIPCION
        defaultCharacteristicShouldNotBeFound("descripcion.doesNotContain=" + DEFAULT_DESCRIPCION);

        // Get all the characteristicList where descripcion does not contain UPDATED_DESCRIPCION
        defaultCharacteristicShouldBeFound("descripcion.doesNotContain=" + UPDATED_DESCRIPCION);
    }


    @Test
    @Transactional
    public void getAllCharacteristicsByServiceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        characteristicRepository.saveAndFlush(characteristic);
        ServiceType serviceType = ServiceTypeResourceIT.createEntity(em);
        em.persist(serviceType);
        em.flush();
        characteristic.setServiceType(serviceType);
        characteristicRepository.saveAndFlush(characteristic);
        Long serviceTypeId = serviceType.getId();

        // Get all the characteristicList where serviceType equals to serviceTypeId
        defaultCharacteristicShouldBeFound("serviceTypeId.equals=" + serviceTypeId);

        // Get all the characteristicList where serviceType equals to serviceTypeId + 1
        defaultCharacteristicShouldNotBeFound("serviceTypeId.equals=" + (serviceTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCharacteristicShouldBeFound(String filter) throws Exception {
        restCharacteristicMockMvc.perform(get("/api/characteristics?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(characteristic.getId().intValue())))
            .andExpect(jsonPath("$.[*].characteristicId").value(hasItem(DEFAULT_CHARACTERISTIC_ID)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));

        // Check, that the count call also returns 1
        restCharacteristicMockMvc.perform(get("/api/characteristics/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCharacteristicShouldNotBeFound(String filter) throws Exception {
        restCharacteristicMockMvc.perform(get("/api/characteristics?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCharacteristicMockMvc.perform(get("/api/characteristics/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCharacteristic() throws Exception {
        // Get the characteristic
        restCharacteristicMockMvc.perform(get("/api/characteristics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCharacteristic() throws Exception {
        // Initialize the database
        characteristicService.save(characteristic);

        int databaseSizeBeforeUpdate = characteristicRepository.findAll().size();

        // Update the characteristic
        Characteristic updatedCharacteristic = characteristicRepository.findById(characteristic.getId()).get();
        // Disconnect from session so that the updates on updatedCharacteristic are not directly saved in db
        em.detach(updatedCharacteristic);
        updatedCharacteristic
            .characteristicId(UPDATED_CHARACTERISTIC_ID)
            .descripcion(UPDATED_DESCRIPCION);

        restCharacteristicMockMvc.perform(put("/api/characteristics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCharacteristic)))
            .andExpect(status().isOk());

        // Validate the Characteristic in the database
        List<Characteristic> characteristicList = characteristicRepository.findAll();
        assertThat(characteristicList).hasSize(databaseSizeBeforeUpdate);
        Characteristic testCharacteristic = characteristicList.get(characteristicList.size() - 1);
        assertThat(testCharacteristic.getCharacteristicId()).isEqualTo(UPDATED_CHARACTERISTIC_ID);
        assertThat(testCharacteristic.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingCharacteristic() throws Exception {
        int databaseSizeBeforeUpdate = characteristicRepository.findAll().size();

        // Create the Characteristic

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCharacteristicMockMvc.perform(put("/api/characteristics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(characteristic)))
            .andExpect(status().isBadRequest());

        // Validate the Characteristic in the database
        List<Characteristic> characteristicList = characteristicRepository.findAll();
        assertThat(characteristicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCharacteristic() throws Exception {
        // Initialize the database
        characteristicService.save(characteristic);

        int databaseSizeBeforeDelete = characteristicRepository.findAll().size();

        // Delete the characteristic
        restCharacteristicMockMvc.perform(delete("/api/characteristics/{id}", characteristic.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Characteristic> characteristicList = characteristicRepository.findAll();
        assertThat(characteristicList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
