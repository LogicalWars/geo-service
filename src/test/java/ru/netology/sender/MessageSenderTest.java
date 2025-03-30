package ru.netology.sender;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageSenderTest {

    private static Stream<Arguments> testSend(){
        return Stream.of(
                Arguments.of("172.0.32.11", "Добро пожаловать"),
                Arguments.of("96.123.45.67", "Welcome")
        );
    }


    @ParameterizedTest
    @MethodSource
    public void testSend(String ip, String expected) {
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp("172.0.32.11")).thenReturn(new Location(null, Country.RUSSIA, null, 0));
        Mockito.when(geoService.byIp("96.123.45.67")).thenReturn(new Location(null, Country.USA, null, 0));


        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String result = messageSender.send(headers);

        assertEquals(result, expected);
    }
}
