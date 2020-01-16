package kr.co.thermometer;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;

public class PkClass implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id ;
	
	private String date ;

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
	
	public boolean equals(Object other) {
		return false;
	}

	public int hashCode() {
		return 0;
	}
	
}
