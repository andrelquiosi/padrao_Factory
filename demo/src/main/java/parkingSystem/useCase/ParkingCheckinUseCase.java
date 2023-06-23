package parkingSystem.useCase;

import java.time.LocalDateTime;

import parkingSystem.parking.model.ParkingCheckin;

public interface ParkingCheckinUseCase {
    void checking(LocalDateTime parkingEntry, String plate);
    void checking(ParkingCheckin checkin);
}
