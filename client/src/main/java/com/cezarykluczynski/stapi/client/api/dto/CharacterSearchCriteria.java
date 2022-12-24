package com.cezarykluczynski.stapi.client.api.dto;

import com.cezarykluczynski.stapi.client.v1.rest.model.Gender;

public class CharacterSearchCriteria extends AbstractPageSortBaseCriteria {

	private String name;

	private Gender gender;

	private Boolean deceased;

	private Boolean hologram;

	private Boolean fictionalCharacter;

	private Boolean mirror;

	private Boolean alternateReality;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Boolean getDeceased() {
		return deceased;
	}

	public void setDeceased(Boolean deceased) {
		this.deceased = deceased;
	}

	public Boolean getHologram() {
		return hologram;
	}

	public void setHologram(Boolean hologram) {
		this.hologram = hologram;
	}

	public Boolean getFictionalCharacter() {
		return fictionalCharacter;
	}

	public void setFictionalCharacter(Boolean fictionalCharacter) {
		this.fictionalCharacter = fictionalCharacter;
	}

	public Boolean getMirror() {
		return mirror;
	}

	public void setMirror(Boolean mirror) {
		this.mirror = mirror;
	}

	public Boolean getAlternateReality() {
		return alternateReality;
	}

	public void setAlternateReality(Boolean alternateReality) {
		this.alternateReality = alternateReality;
	}

}
