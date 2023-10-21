package com.pino.java_unit_test_sample.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class IdentityNumberUtilsTest {

    @DisplayName("Check identity number is valid")
    @ParameterizedTest
    @CsvSource({
        "123       , false",
        "0111447556, false",
        "01114+7556, false",
        "R122478963, true",
        "A111447556, true",
        "L325848552, false",
        "K288094386, true",
    })
    void testIsValid(String identityNumber, boolean expected) {
        var actual = IdentityNumberUtils.isValid(identityNumber);
        assertThat(actual).isEqualTo(expected);
    }
}
