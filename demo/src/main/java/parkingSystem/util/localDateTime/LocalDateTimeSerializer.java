package parkingSystem.util.localDateTime;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class LocalDateTimeSerializer implements JsonSerializer < LocalDateTime > 
{
	public static final 
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
    											   .withLocale(Locale.forLanguageTag("pt_BR"));

    @Override
    public JsonElement serialize(LocalDateTime localDateTime, 
    		                     Type srcType, 
    		                     JsonSerializationContext context )
    {
        return new JsonPrimitive(formatter.format(localDateTime));
    }
}