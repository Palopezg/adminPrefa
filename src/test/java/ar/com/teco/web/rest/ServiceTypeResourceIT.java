package ar.com.teco.web.rest;

import ar.com.teco.AdminPrefaApp;
import ar.com.teco.domain.ServiceType;
import ar.com.teco.repository.ServiceTypeRepository;
import ar.com.teco.service.ServiceTypeService;
import ar.com.teco.service.dto.ServiceTypeCriteria;
import ar.com.teco.service.ServiceTypeQueryService;

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
 * Integration tests for the {@link ServiceTypeResource} REST controller.
 */
@SpringBootTest(classes = AdminPrefaApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ServiceTypeResourceIT {

    private static final String DEFAULT_SERVICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    @Autowired
    private ServiceTypeService serviceTypeService;

    @Autowired
    private ServiceTypeQueryService serviceTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceTypeMockMvc;

    private ServiceType serviceType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceType createEntity(EntityManager em) {
        ServiceType serviceType = new ServiceType()
            .serviceId(DEFAULT_SERVICE_ID)
            .descripcion(DEFAULT_DESCRIPCION);
        return serviceType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceType createUpdatedEntity(EntityManager em) {
        ServiceType serviceType = new ServiceType()
            .serviceId(UPDATED_SERVICE_ID)
            .descripcion(UPDATED_DESCRIPCION);
        return serviceType;
    }

    @BeforeEach
    public void initTest() {
        serviceType = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceType() throws Exception {
        int databaseSizeBeforeCreate = serviceTypeRepository.findAll().size();

        // Create the ServiceType
        restServiceTypeMockMvc.perform(post("/api/service-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceType)))
            .andExpect(status().isCreated());

        // Validate the ServiceType in the database
        List<ServiceType> serviceTypeList = serviceTypeRepository.findAll();
        assertThat(serviceTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceType testServiceType = serviceTypeList.get(serviceTypeList.size() - 1);
        assertThat(testServiceType.getServiceId()).isEqualTo(DEFAULT_SERVICE_ID);
        assertThat(testServiceType.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createServiceTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceTypeRepository.findAll().size();

        // Create the ServiceType with an existing ID
        serviceType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceTypeMockMvc.perform(post("/api/service-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceType)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceType in the database
        List<ServiceType> serviceTypeList = serviceTypeRepository.findAll();
        assertThat(serviceTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllServiceTypes() throws Exception {
        // Initialize the database
        serviceTypeRepository.saveAndFlush(serviceType);

        // Get all the serviceTypeList
        restServiceTypeMockMvc.perform(get("/api/service-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceId").value(hasItem(DEFAULT_SERVICE_ID)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }
    
    @Test
    @Transactional
    public void getServiceType() throws Exception {
        // Initialize the database
        serviceTypeRepository.saveAndFlush(serviceType);

        // Get the serviceType
        restServiceTypeMockMvc.perform(get("/api/service-types/{id}", serviceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceType.getId().intValue()))
            .andExpect(jsonPath("$.serviceId").value(DEFAULT_SERVICE_ID))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }


    @Test
    @Transactional
    public void getServiceTypesByIdFiltering() throws Exception {
        // Initialize the database
        serviceTypeRepository.saveAndFlush(serviceType);

        Long id = serviceType.getId();

        defaultServiceTypeShouldBeFound("id.equals=" + id);
        defaultServiceTypeShouldNotBeFound("id.notEquals=" + id);

        defaultServiceTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultServiceTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultServiceTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultServiceTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllServiceTypesByServiceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceTypeRepository.saveAndFlush(serviceType);

        // Get all the serviceTypeList where serviceId equals to DEFAULT_SERVICE_ID
        defaultServiceTypeShouldBeFound("serviceId.equals=" + DEFAULT_SERVICE_ID);

        // Get all the serviceTypeList where serviceId equals to UPDATED_SERVICE_ID
        defaultServiceTypeShouldNotBeFound("serviceId.equals=" + UPDATED_SERVICE_ID);
    }

    @Test
    @Transactional
    public void getAllServiceTypesByServiceIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceTypeRepository.saveAndFlush(serviceType);

        // Get all the serviceTypeList where serviceId not equals to DEFAULT_SERVICE_ID
        defaultServiceTypeShouldNotBeFound("serviceId.notEquals=" + DEFAULT_SERVICE_ID);

        // Get all the serviceTypeList where serviceId not equals to UPDATED_SERVICE_ID
        defaultServiceTypeShouldBeFound("serviceId.notEquals=" + UPDATED_SERVICE_ID);
    }

    @Test
    @Transactional
    public void getAllServiceTypesByServiceIdIsInShouldWork() throws Exception {
        // Initialize the database
        serviceTypeRepository.saveAndFlush(serviceType);

        // Get all the serviceTypeList where serviceId in DEFAULT_SERVICE_ID or UPDATED_SERVICE_ID
        defaultServiceTypeShouldBeFound("serviceId.in=" + DEFAULT_SERVICE_ID + "," + UPDATED_SERVICE_ID);

        // Get all the serviceTypeList where serviceId equals to UPDATED_SERVICE_ID
        defaultServiceTypeShouldNotBeFound("serviceId.in=" + UPDATED_SERVICE_ID);
    }

    @Test
    @Transactional
    public void getAllServiceTypesByServiceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceTypeRepository.saveAndFlush(serviceType);

        // Get all the serviceTypeList where serviceId is not null
        defaultServiceTypeShouldBeFound("serviceId.specified=true");

        // Get all the serviceTypeList where serviceId is null
        defaultServiceTypeShouldNotBeFound("serviceId.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceTypesByServiceIdContainsSomething() throws Exception {
        // Initialize the database
        serviceTypeRepository.saveAndFlush(serviceType);

        // Get all the serviceTypeList where serviceId contains DEFAULT_SERVICE_ID
        defaultServiceTypeShouldBeFound("serviceId.contains=" + DEFAULT_SERVICE_ID);

        // Get all the serviceTypeList where serviceId contains UPDATED_SERVICE_ID
        defaultServiceTypeShouldNotBeFound("serviceId.contains=" + UPDATED_SERVICE_ID);
    }

    @Test
    @Transactional
    public void getAllServiceTypesByServiceIdNotContainsSomething() throws Exception {
        // Initialize the database
        serviceTypeRepository.saveAndFlush(serviceType);

        // Get all the serviceTypeList where serviceId does not contain DEFAULT_SERVICE_ID
        defaultServiceTypeShouldNotBeFound("serviceId.doesNotContain=" + DEFAULT_SERVICE_ID);

        // Get all the serviceTypeList where serviceId does not contain UPDATED_SERVICE_ID
        defaultServiceTypeShouldBeFound("serviceId.doesNotContain=" + UPDATED_SERVICE_ID);
    }


    @Test
    @Transactional
    public void getAllServiceTypesByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceTypeRepository.saveAndFlush(serviceType);

        // Get all the serviceTypeList where descripcion equals to DEFAULT_DESCRIPCION
        defaultServiceTypeShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the serviceTypeList where descripcion equals to UPDATED_DESCRIPCION
        defaultServiceTypeShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllServiceTypesByDescripcionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceTypeRepository.saveAndFlush(serviceType);

        // Get all the serviceTypeList where descripcion not equals to DEFAULT_DESCRIPCION
        defaultServiceTypeShouldNotBeFound("descripcion.notEquals=" + DEFAULT_DESCRIPCION);

        // Get all the serviceTypeList where descripcion not equals to UPDATED_DESCRIPCION
        defaultServiceTypeShouldBeFound("descripcion.notEquals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllServiceTypesByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceTypeRepository.saveAndFlush(serviceType);

        // Get all the serviceTypeList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultServiceTypeShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the serviceTypeList where descripcion equals to UPDATED_DESCRIPCION
        defaultServiceTypeShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllServiceTypesByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceTypeRepository.saveAndFlush(serviceType);

        // Get all the serviceTypeList where descripcion is not null
        defaultServiceTypeShouldBeFound("descripcion.specified=true");

        // Get all the serviceTypeList where descripcion is null
        defaultServiceTypeShouldNotBeFound("descripcion.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceTypesByDescripcionContainsSomething() throws Exception {
        // Initialize the database
        serviceTypeRepository.saveAndFlush(serviceType);

        // Get all the serviceTypeList where descripcion contains DEFAULT_DESCRIPCION
        defaultServiceTypeShouldBeFound("descripcion.contains=" + DEFAULT_DESCRIPCION);

        // Get all the serviceTypeList where descripcion contains UPDATED_DESCRIPCION
        defaultServiceTypeShouldNotBeFound("descripcion.contains=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllServiceTypesByDescripcionNotContainsSomething() throws Exception {
        // Initialize the database
        serviceTypeRepository.saveAndFlush(serviceType);

        // Get all the serviceTypeList where descripcion does not contain DEFAULT_DESCRIPCION
        defaultServiceTypeShouldNotBeFound("descripcion.doesNotContain=" + DEFAULT_DESCRIPCION);

        // Get all the serviceTypeList where descripcion does not contain UPDATED_DESCRIPCION
        defaultServiceTypeShouldBeFound("descripcion.doesNotContain=" + UPDATED_DESCRIPCION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceTypeShouldBeFound(String filter) throws Exception {
        restServiceTypeMockMvc.perform(get("/api/service-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceId").value(hasItem(DEFAULT_SERVICE_ID)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));

        // Check, that the count call also returns 1
        restServiceTypeMockMvc.perform(get("/api/service-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceTypeShouldNotBeFound(String filter) throws Exception {
        restServiceTypeMockMvc.perform(get("/api/service-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceTypeMockMvc.perform(get("/api/service-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingServiceType() throws Exception {
        // Get the serviceType
        restServiceTypeMockMvc.perform(get("/api/service-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceType() throws Exception {
        // Initialize the database
        serviceTypeService.save(serviceType);

        int databaseSizeBeforeUpdate = serviceTypeRepository.findAll().size();

        // Update the serviceType
        ServiceType updatedServiceType = serviceTypeRepository.findById(serviceType.getId()).get();
        // Disconnect from session so that the updates on updatedServiceType are not directly saved in db
        em.detach(updatedServiceType);
        updatedServiceType
            .serviceId(UPDATED_SERVICE_ID)
            .descripcion(UPDATED_DESCRIPCION);

        restServiceTypeMockMvc.perform(put("/api/service-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedServiceType)))
            .andExpect(status().isOk());

        // Validate the ServiceType in the database
        List<ServiceType> serviceTypeList = serviceTypeRepository.findAll();
        assertThat(serviceTypeList).hasSize(databaseSizeBeforeUpdate);
        ServiceType testServiceType = serviceTypeList.get(serviceTypeList.size() - 1);
        assertThat(testServiceType.getServiceId()).isEqualTo(UPDATED_SERVICE_ID);
        assertThat(testServiceType.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceType() throws Exception {
        int databaseSizeBeforeUpdate = serviceTypeRepository.findAll().size();

        // Create the ServiceType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceTypeMockMvc.perform(put("/api/service-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceType)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceType in the database
        List<ServiceType> serviceTypeList = serviceTypeRepository.findAll();
        assertThat(serviceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceType() throws Exception {
        // Initialize the database
        serviceTypeService.save(serviceType);

        int databaseSizeBeforeDelete = serviceTypeRepository.findAll().size();

        // Delete the serviceType
        restServiceTypeMockMvc.perform(delete("/api/service-types/{id}", serviceType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceType> serviceTypeList = serviceTypeRepository.findAll();
        assertThat(serviceTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
