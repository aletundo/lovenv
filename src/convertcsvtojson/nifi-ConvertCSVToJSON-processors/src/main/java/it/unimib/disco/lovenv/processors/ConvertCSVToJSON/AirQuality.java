package it.unimib.disco.lovenv.processors.ConvertCSVToJSON;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AirQuality {
	@JsonProperty("TIMESTAMP")
	Date timestamp;
	
	@JsonProperty("PM2_5")
	float pm25;
	
	@JsonProperty("PM10")
	float pm10;
	
	@JsonProperty("NOX")
	float nox;
	
	@JsonProperty("O3")
	float o3;
	
	@JsonProperty("NO")
	float no;
	
	@JsonProperty("NO2")
	float no2;
	
	@JsonProperty("CO")
	float co;
	
	@JsonProperty("BENZENE")
	float benzene;
	
	@JsonProperty("PBLM")
	float pblm;
	
	@JsonProperty("SO2")
	float so2;
	
	@JsonProperty("IPA_TOT")
	float ipaTot;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getTimestamp() {
		return timestamp;
	}
	
	@JsonDeserialize(using = JsonDateDeserializer.class)
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public float getPm25() {
		return pm25;
	}

	public void setPm25(float pm25) {
		this.pm25 = pm25;
	}

	public float getPm10() {
		return pm10;
	}

	public void setPm10(float pm10) {
		this.pm10 = pm10;
	}

	public float getNox() {
		return nox;
	}

	public void setNox(float nox) {
		this.nox = nox;
	}

	public float getO3() {
		return o3;
	}

	public void setO3(float o3) {
		this.o3 = o3;
	}

	public float getNo() {
		return no;
	}

	public void setNo(float no) {
		this.no = no;
	}

	public float getNo2() {
		return no2;
	}

	public void setNo2(float no2) {
		this.no2 = no2;
	}

	public float getCo() {
		return co;
	}

	public void setCo(float co) {
		this.co = co;
	}

	public float getBenzene() {
		return benzene;
	}

	public void setBenzene(float benzene) {
		this.benzene = benzene;
	}

	public float getPblm() {
		return pblm;
	}

	public void setPblm(float pblm) {
		this.pblm = pblm;
	}

	public float getSo2() {
		return so2;
	}

	public void setSo2(float so2) {
		this.so2 = so2;
	}

	public float getIpaTot() {
		return ipaTot;
	}

	public void setIpaTot(float ipaTot) {
		this.ipaTot = ipaTot;
	}
}
