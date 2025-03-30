package ru.netology.i18n;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.netology.entity.Country;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalizationServiceTest {

    private final LocalizationServiceImpl service = new LocalizationServiceImpl();

    @ParameterizedTest
    @EnumSource(Country.class)
    void testLocale(Country country) {
        String expected = (country == Country.RUSSIA) ? "Добро пожаловать" : "Welcome";
        assertEquals(expected, service.locale(country));
    }
}
