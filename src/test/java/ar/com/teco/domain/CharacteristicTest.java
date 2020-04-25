package ar.com.teco.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ar.com.teco.web.rest.TestUtil;

public class CharacteristicTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Characteristic.class);
        Characteristic characteristic1 = new Characteristic();
        characteristic1.setId(1L);
        Characteristic characteristic2 = new Characteristic();
        characteristic2.setId(characteristic1.getId());
        assertThat(characteristic1).isEqualTo(characteristic2);
        characteristic2.setId(2L);
        assertThat(characteristic1).isNotEqualTo(characteristic2);
        characteristic1.setId(null);
        assertThat(characteristic1).isNotEqualTo(characteristic2);
    }
}
