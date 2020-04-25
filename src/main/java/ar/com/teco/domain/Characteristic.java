package ar.com.teco.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Characteristic.
 */
@Entity
@Table(name = "characteristic")
public class Characteristic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "characteristic_id", nullable = false)
    private String characteristicId;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
    @JsonIgnoreProperties("characteristics")
    private ServiceType serviceType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCharacteristicId() {
        return characteristicId;
    }

    public Characteristic characteristicId(String characteristicId) {
        this.characteristicId = characteristicId;
        return this;
    }

    public void setCharacteristicId(String characteristicId) {
        this.characteristicId = characteristicId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Characteristic descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public Characteristic serviceType(ServiceType serviceType) {
        this.serviceType = serviceType;
        return this;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Characteristic)) {
            return false;
        }
        return id != null && id.equals(((Characteristic) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Characteristic{" +
            "id=" + getId() +
            ", characteristicId='" + getCharacteristicId() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
