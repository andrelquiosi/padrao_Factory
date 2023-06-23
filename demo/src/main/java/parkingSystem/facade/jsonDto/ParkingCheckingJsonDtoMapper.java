package parkingSystem.facade.jsonDto;

import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import parkingSystem.parking.model.ParkingCheckin;
import parkingSystem.util.localDateTime.LocalDateTimeDeserializer;
import parkingSystem.util.localDateTime.LocalDateTimeSerializer;

public class ParkingCheckingJsonDtoMapper {

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
            .create();

    public ParkingCheckin fromJsonDto(String jsonDto) {
        ParkingCheckin parkingCheckin = gson.fromJson(jsonDto, ParkingCheckin.class);

        return parkingCheckin;
    }

    public String toJsonDto(ParkingCheckin parkingCheckin) {
        String jsonDto = gson.toJson(parkingCheckin);
        return jsonDto;
    }

}
