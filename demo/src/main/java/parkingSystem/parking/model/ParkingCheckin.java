package parkingSystem.parking.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ParkingCheckin {

    private LocalDateTime dateTime;
    private String plate;

    public ParkingCheckin() {
    }

    public ParkingCheckin(LocalDateTime dateTome, String plate) {
        this.dateTime = dateTome;
        this.plate = plate;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTome) {
        this.dateTime = dateTome;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String toString(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").withLocale(new Locale("pt", "BR"));
        String sDateTime = dateTime.format(formatter);

        return String.format("{\"entryDateTime\":\"%s\",\"plate\":\"%s\"}", sDateTime,plate); 
        
    }

}
