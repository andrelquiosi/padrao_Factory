package parkingSystem.facade.jsonDto;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import parkingSystem.parking.model.ParkingCheckin;

public class ParkingCheckingJsonDtoMapperTest {
    @Test
    void shouldConvertParkingCheckinToJsonDto() {
        LocalDateTime localDate = LocalDateTime.now();
        String plate = "AXE-1234";
        ParkingCheckin parkingCheckin = new ParkingCheckin(localDate, plate);

        ParkingCheckingJsonDtoMapper mapper = new ParkingCheckingJsonDtoMapper();
        String jsonDto = mapper.toJsonDto(parkingCheckin);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss")
                .withLocale(new Locale("pt", "BR"));

        String sDateTime = formatter.format(localDate);
        String expectedJson = String.format("{\"dateTime\":\"%s\",\"plate\":\"%s\"}", sDateTime, plate);

        assertEquals(expectedJson, jsonDto);

    }

    @Test
    void shouldConvertJsonDtoToParkingCheching() {

        String dateTime = "20-06-2023 07:41:00";
        String plate = "AXE-7832";

        String jsonDtoFormat = "{\"dateTime\":\"%s\",\"plate\":\"%s\"}";
        String jsonDto = String.format(jsonDtoFormat, dateTime, plate);

        ParkingCheckingJsonDtoMapper mapper = new ParkingCheckingJsonDtoMapper();
        ParkingCheckin parkingCheckin = mapper.fromJsonDto(jsonDto);
        assertEquals(plate, parkingCheckin.getPlate());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss")
                .withLocale(new Locale("pt", "BR"));

        assertEquals(dateTime, formatter.format(parkingCheckin.getDateTime()));

    }
}
