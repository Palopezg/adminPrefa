package ar.com.teco.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ar.com.teco.web.rest.TestUtil;

public class TipoDecoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDeco.class);
        TipoDeco tipoDeco1 = new TipoDeco();
        tipoDeco1.setId(1L);
        TipoDeco tipoDeco2 = new TipoDeco();
        tipoDeco2.setId(tipoDeco1.getId());
        assertThat(tipoDeco1).isEqualTo(tipoDeco2);
        tipoDeco2.setId(2L);
        assertThat(tipoDeco1).isNotEqualTo(tipoDeco2);
        tipoDeco1.setId(null);
        assertThat(tipoDeco1).isNotEqualTo(tipoDeco2);
    }
}
