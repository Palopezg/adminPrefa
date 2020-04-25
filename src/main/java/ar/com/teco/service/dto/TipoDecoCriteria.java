package ar.com.teco.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import ar.com.teco.domain.enumeration.Tecnologia;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ar.com.teco.domain.TipoDeco} entity. This class is used
 * in {@link ar.com.teco.web.rest.TipoDecoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tipo-decos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TipoDecoCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Tecnologia
     */
    public static class TecnologiaFilter extends Filter<Tecnologia> {

        public TecnologiaFilter() {
        }

        public TecnologiaFilter(TecnologiaFilter filter) {
            super(filter);
        }

        @Override
        public TecnologiaFilter copy() {
            return new TecnologiaFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tipoDeco;

    private TecnologiaFilter tecnologia;

    private StringFilter multicast;

    public TipoDecoCriteria() {
    }

    public TipoDecoCriteria(TipoDecoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tipoDeco = other.tipoDeco == null ? null : other.tipoDeco.copy();
        this.tecnologia = other.tecnologia == null ? null : other.tecnologia.copy();
        this.multicast = other.multicast == null ? null : other.multicast.copy();
    }

    @Override
    public TipoDecoCriteria copy() {
        return new TipoDecoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTipoDeco() {
        return tipoDeco;
    }

    public void setTipoDeco(StringFilter tipoDeco) {
        this.tipoDeco = tipoDeco;
    }

    public TecnologiaFilter getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(TecnologiaFilter tecnologia) {
        this.tecnologia = tecnologia;
    }

    public StringFilter getMulticast() {
        return multicast;
    }

    public void setMulticast(StringFilter multicast) {
        this.multicast = multicast;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TipoDecoCriteria that = (TipoDecoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tipoDeco, that.tipoDeco) &&
            Objects.equals(tecnologia, that.tecnologia) &&
            Objects.equals(multicast, that.multicast);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tipoDeco,
        tecnologia,
        multicast
        );
    }

    @Override
    public String toString() {
        return "TipoDecoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tipoDeco != null ? "tipoDeco=" + tipoDeco + ", " : "") +
                (tecnologia != null ? "tecnologia=" + tecnologia + ", " : "") +
                (multicast != null ? "multicast=" + multicast + ", " : "") +
            "}";
    }

}
