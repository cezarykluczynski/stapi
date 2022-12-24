package com.cezarykluczynski.stapi.client.api.dto;

public class AnimalSearchCriteria extends AbstractPageSortBaseCriteria {

	private String name;

	private Boolean earthAnimal;

	private Boolean earthInsect;

	private Boolean avian;

	private Boolean canine;

	private Boolean feline;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getEarthAnimal() {
		return earthAnimal;
	}

	public void setEarthAnimal(Boolean earthAnimal) {
		this.earthAnimal = earthAnimal;
	}

	public Boolean getEarthInsect() {
		return earthInsect;
	}

	public void setEarthInsect(Boolean earthInsect) {
		this.earthInsect = earthInsect;
	}

	public Boolean getAvian() {
		return avian;
	}

	public void setAvian(Boolean avian) {
		this.avian = avian;
	}

	public Boolean getCanine() {
		return canine;
	}

	public void setCanine(Boolean canine) {
		this.canine = canine;
	}

	public Boolean getFeline() {
		return feline;
	}

	public void setFeline(Boolean feline) {
		this.feline = feline;
	}

}
