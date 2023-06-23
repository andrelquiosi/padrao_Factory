package parkingSystem.useCase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import parkingSystem.parking.model.ParkingCheckin;

public class ParkingCheckinUseCaseImpl implements ParkingCheckinUseCase {

    @Override
    public void checking(LocalDateTime parkingEntry, String plate) {
        checking(new ParkingCheckin(parkingEntry, plate));
    }

    @Override
    public void checking(ParkingCheckin checkin) {
        System.out.println("validando Dados");

        System.out.println("Registrando a entrada");
        System.out.println("Veículo: " + checkin.getPlate());

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter hourFsormatter = DateTimeFormatter.ofPattern("HH'hrs' mm'min'");

        System.out.printf("Dia: %s as %s\n\r", dateFormatter.format(checkin.getDateTime()),
                hourFsormatter.format(checkin.getDateTime()));
    }

}
