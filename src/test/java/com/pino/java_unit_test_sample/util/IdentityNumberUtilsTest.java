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
        "L325848552, false",
        "A111585445, false",
        "J261590753, true",
        "D194526855, true",
        "A131020337, true",
        "B217233648, true",
        "C165182679, true",
        "D129562403, true",
        "E206228116, true",
        "F206108281, true",
        "G165415515, true",
        "H294862638, true",
        "I229070992, true",
        "J199894648, true",
        "K119365103, true",
        "L111677991, true",
        "M233963168, true",
        "N121469556, true",
        "O277218865, true",
        "P206639484, true",
        "Q143698722, true",
        "R126920360, true",
        "S108937920, true",
        "T204632758, true",
        "U256040683, true",
        "V256330405, true",
        "W231764372, true",
        "X235906123, true",
        "Z195468172, true",
    })
    void testIsValid(String identityNumber, boolean expected) {
        var actual = IdentityNumberUtils.isValid(identityNumber);
        assertThat(actual).isEqualTo(expected);
    }
}
