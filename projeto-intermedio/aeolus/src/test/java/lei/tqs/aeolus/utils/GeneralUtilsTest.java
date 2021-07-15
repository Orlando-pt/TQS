package lei.tqs.aeolus.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class GeneralUtilsTest {

    @Test
    void verifyLatitudeTest() {
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
    void verifyLongitudeTest() {
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

    @Test
    void cropCoordinateTest() {
        Assertions.assertThat(
                GeneralUtils.cropCoordinate("10")
        ).isEqualTo("10");

        Assertions.assertThat(
                GeneralUtils.cropCoordinate("40.1")
        ).isEqualTo("40.1");

        Assertions.assertThat(
                GeneralUtils.cropCoordinate("40.640632")
        ).isEqualTo("40.6406");
    }
}