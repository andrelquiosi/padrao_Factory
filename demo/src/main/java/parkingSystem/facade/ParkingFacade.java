package parkingSystem.facade;

import parkingSystem.facade.jsonDto.ParkingCheckingJsonDtoMapper;
import parkingSystem.parking.model.ParkingCheckin;
import parkingSystem.useCase.ParkingCheckinUseCase;
import parkingSystem.useCase.ParkingCheckinUseCaseImpl;

public class ParkingFacade {

    public void parkingCheckin(String chekingJsonDto){

        ParkingCheckingJsonDtoMapper mapper = new ParkingCheckingJsonDtoMapper();
        ParkingCheckin parkingCheckin = mapper.fromJsonDto(chekingJsonDto);
        ParkingCheckinUseCase useCase = new ParkingCheckinUseCaseImpl();
        useCase.checking(parkingCheckin); 
    }
    
    public void parkingCheckout(String checkoutJsonDto){
        
    }
}
