package parkingSystem.facade;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParkingFacadeTest {

    ByteArrayOutputStream baos;
    PrintStream out = System.out;

    @BeforeEach
    void redirectStdout() {
        baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
    }

    @AfterEach
    void restoreStdout() {
        System.setOut(out);
    }

    @Test
    void shouldExecuteParkinCheckinUseCaseWithoutErrors() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss")
                .withLocale(new Locale("pt", "BR"));

        LocalDateTime dateTime = LocalDateTime.now();
        String plate = "AXE-1234";
        String sDateTime = formatter.format(dateTime);
        String checkingJsonDto = String.format("{\"dateTime\":\"%s\",\"plate\":\"%s\"}", sDateTime, plate);

        ParkingFacade facade = new ParkingFacade();
        facade.parkingCheckin(checkingJsonDto);

        String printedText = baos.toString();
        assertTrue(printedText.contains(plate));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyy");
        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH'hrs' mm'min'");

        String formattedDate = dateFormatter.format(dateTime);
        assertTrue(printedText.contains(formattedDate));

        String formattedHour = hourFormatter.format(dateTime);
        assertTrue(printedText.contains(formattedHour));
    }
}
