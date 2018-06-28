package it.unimib.disco.lovenv.processors.ConvertCSVToJSON;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class JsonDateDeserializer extends JsonDeserializer<Date> {
	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
		if (jp.getCurrentToken().equals(JsonToken.VALUE_STRING)) {
			try {
				return df.parse(jp.getText().toString());
			} catch (ParseException e) {
				throw new IOException(e.getMessage());
			}
		}
		return null;
	}

}
