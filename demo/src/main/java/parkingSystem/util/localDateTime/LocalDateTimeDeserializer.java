package parkingSystem.util.localDateTime;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class LocalDateTimeDeserializer implements JsonDeserializer < LocalDateTime > 
{
	public static final 
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
    											   .withLocale(Locale.forLanguageTag("pt_BR"));
    //------------------------------------------------------------------------------------------
	@Override
    public 
    LocalDateTime deserialize( JsonElement json, 
                               Type typeOfT, 
                               JsonDeserializationContext context )
    throws JsonParseException 
    {
        return LocalDateTime.parse( json.getAsString(), formatter);
    }
}