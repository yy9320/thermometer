package kr.co.thermometer;

import java.io.Serializable;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@IdClass(PkClass.class)
@Table(name="TEMPERATURE")
public class Temperature {
	
	@Id
	@Column(name = "ID")
	private String id ;

	@Id
	private String date;
	
	private Integer temperature;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getTemperature() {
		return temperature;
	}

	public void setTemperature(Integer temperature) {
		this.temperature = temperature;
	}
	

}
