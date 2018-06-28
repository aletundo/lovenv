package it.unimib.disco.lovenv.processors.ConvertCSVToJSON;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonDateSerializer extends JsonSerializer<Date> {
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider sp) throws IOException {
		final String formattedDate = df.format(date.getTime());
		gen.writeString(formattedDate);
	}

}
