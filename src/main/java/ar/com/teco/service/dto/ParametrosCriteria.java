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
 * Criteria class for the {@link ar.com.teco.domain.Parametros} entity. This class is used
 * in {@link ar.com.teco.web.rest.ParametrosResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /parametros?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ParametrosCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter parametro;

    private StringFilter valor;

    public ParametrosCriteria() {
    }

    public ParametrosCriteria(ParametrosCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.parametro = other.parametro == null ? null : other.parametro.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
    }

    @Override
    public ParametrosCriteria copy() {
        return new ParametrosCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getParametro() {
        return parametro;
    }

    public void setParametro(StringFilter parametro) {
        this.parametro = parametro;
    }

    public StringFilter getValor() {
        return valor;
    }

    public void setValor(StringFilter valor) {
        this.valor = valor;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ParametrosCriteria that = (ParametrosCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(parametro, that.parametro) &&
            Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        parametro,
        valor
        );
    }

    @Override
    public String toString() {
        return "ParametrosCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (parametro != null ? "parametro=" + parametro + ", " : "") +
                (valor != null ? "valor=" + valor + ", " : "") +
            "}";
    }

}
