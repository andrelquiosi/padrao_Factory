package parkingSystem.util.localDateTime;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class LocalDateSerializer implements JsonSerializer < LocalDate > 
{
    public static final 
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    											   .withLocale(new Locale("pt","BR"));

    @Override
    public JsonElement serialize( LocalDate localDate, 
    		                      Type srcType, 
    		                      JsonSerializationContext context) 
    {
        return new JsonPrimitive(formatter.format(localDate));
    }
}