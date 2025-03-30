package ru.netology.geo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeoServiceTest {

    private final GeoServiceImpl geoService = new GeoServiceImpl();

    private static Stream<Arguments> ipProvider() {
        return Stream.of(
                Arguments.of(GeoServiceImpl.LOCALHOST, null),
                Arguments.of(GeoServiceImpl.MOSCOW_IP, Country.RUSSIA),
                Arguments.of(GeoServiceImpl.NEW_YORK_IP, Country.USA),
                Arguments.of("172.123.45.67", Country.RUSSIA),
                Arguments.of("96.123.45.67", Country.USA),
                Arguments.of("203.0.113.0", null) // случай неизвестного IP
        );
    }

    @ParameterizedTest
    @MethodSource("ipProvider")
    void testByIp(String ip, Country expected) {
        Location location = geoService.byIp(ip);
        Country actual = (location != null) ? location.getCountry() : null;
        assertEquals(expected, actual);
    }


}
