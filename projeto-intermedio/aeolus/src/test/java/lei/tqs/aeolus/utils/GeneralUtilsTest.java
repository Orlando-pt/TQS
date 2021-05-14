package lei.tqs.aeolus.utils;

import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class GeneralUtilsTest {

    @Test
    void verifyLatitude() {
        Assertions.assertThat(
                GeneralUtils.verifyLatitude("91")
        ).isFalse();

        Assertions.assertThat(
                GeneralUtils.verifyLatitude("88asd")
        ).isFalse();

        Assertions.assertThat(
                GeneralUtils.verifyLatitude("88")
        ).isTrue();
    }

    @Test
    void verifyLongitude() {
        Assertions.assertThat(
                GeneralUtils.verifyLongitude("181")
        ).isFalse();

        Assertions.assertThat(
                GeneralUtils.verifyLongitude("88asd")
        ).isFalse();

        Assertions.assertThat(
                GeneralUtils.verifyLongitude("88")
        ).isTrue();
    }
}