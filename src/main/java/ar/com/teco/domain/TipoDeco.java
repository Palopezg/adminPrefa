package ar.com.teco.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import ar.com.teco.domain.enumeration.Tecnologia;

/**
 * A TipoDeco.
 */
@Entity
@Table(name = "tipo_deco")
public class TipoDeco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "tipo_deco", nullable = false)
    private String tipoDeco;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tecnologia", nullable = false)
    private Tecnologia tecnologia;

    @NotNull
    @Column(name = "multicast", nullable = false)
    private String multicast;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoDeco() {
        return tipoDeco;
    }

    public TipoDeco tipoDeco(String tipoDeco) {
        this.tipoDeco = tipoDeco;
        return this;
    }

    public void setTipoDeco(String tipoDeco) {
        this.tipoDeco = tipoDeco;
    }

    public Tecnologia getTecnologia() {
        return tecnologia;
    }

    public TipoDeco tecnologia(Tecnologia tecnologia) {
        this.tecnologia = tecnologia;
        return this;
    }

    public void setTecnologia(Tecnologia tecnologia) {
        this.tecnologia = tecnologia;
    }

    public String getMulticast() {
        return multicast;
    }

    public TipoDeco multicast(String multicast) {
        this.multicast = multicast;
        return this;
    }

    public void setMulticast(String multicast) {
        this.multicast = multicast;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoDeco)) {
            return false;
        }
        return id != null && id.equals(((TipoDeco) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TipoDeco{" +
            "id=" + getId() +
            ", tipoDeco='" + getTipoDeco() + "'" +
            ", tecnologia='" + getTecnologia() + "'" +
            ", multicast='" + getMulticast() + "'" +
            "}";
    }
}
