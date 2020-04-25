package ar.com.teco.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ar.com.teco.domain.Characteristic} entity. This class is used
 * in {@link ar.com.teco.web.rest.CharacteristicResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /characteristics?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CharacteristicCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter characteristicId;

    private StringFilter descripcion;

    private LongFilter serviceTypeId;

    public CharacteristicCriteria() {
    }

    public CharacteristicCriteria(CharacteristicCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.characteristicId = other.characteristicId == null ? null : other.characteristicId.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
        this.serviceTypeId = other.serviceTypeId == null ? null : other.serviceTypeId.copy();
    }

    @Override
    public CharacteristicCriteria copy() {
        return new CharacteristicCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCharacteristicId() {
        return characteristicId;
    }

    public void setCharacteristicId(StringFilter characteristicId) {
        this.characteristicId = characteristicId;
    }

    public StringFilter getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(StringFilter descripcion) {
        this.descripcion = descripcion;
    }

    public LongFilter getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(LongFilter serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CharacteristicCriteria that = (CharacteristicCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(characteristicId, that.characteristicId) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(serviceTypeId, that.serviceTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        characteristicId,
        descripcion,
        serviceTypeId
        );
    }

    @Override
    public String toString() {
        return "CharacteristicCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (characteristicId != null ? "characteristicId=" + characteristicId + ", " : "") +
                (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
                (serviceTypeId != null ? "serviceTypeId=" + serviceTypeId + ", " : "") +
            "}";
    }

}
