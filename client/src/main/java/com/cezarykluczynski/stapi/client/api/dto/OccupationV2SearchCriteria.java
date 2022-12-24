package com.cezarykluczynski.stapi.client.api.dto;

public class OccupationV2SearchCriteria extends AbstractPageSortBaseCriteria {

	private String name;

	private Boolean artsOccupation;

	private Boolean communicationOccupation;

	private Boolean economicOccupation;

	private Boolean educationOccupation;

	private Boolean entertainmentOccupation;

	private Boolean illegalOccupation;

	private Boolean legalOccupation;

	private Boolean medicalOccupation;

	private Boolean scientificOccupation;

	private Boolean sportsOccupation;

	private Boolean victualOccupation;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getArtsOccupation() {
		return artsOccupation;
	}

	public void setArtsOccupation(Boolean artsOccupation) {
		this.artsOccupation = artsOccupation;
	}

	public Boolean getCommunicationOccupation() {
		return communicationOccupation;
	}

	public void setCommunicationOccupation(Boolean communicationOccupation) {
		this.communicationOccupation = communicationOccupation;
	}

	public Boolean getEconomicOccupation() {
		return economicOccupation;
	}

	public void setEconomicOccupation(Boolean economicOccupation) {
		this.economicOccupation = economicOccupation;
	}

	public Boolean getEducationOccupation() {
		return educationOccupation;
	}

	public void setEducationOccupation(Boolean educationOccupation) {
		this.educationOccupation = educationOccupation;
	}

	public Boolean getEntertainmentOccupation() {
		return entertainmentOccupation;
	}

	public void setEntertainmentOccupation(Boolean entertainmentOccupation) {
		this.entertainmentOccupation = entertainmentOccupation;
	}

	public Boolean getIllegalOccupation() {
		return illegalOccupation;
	}

	public void setIllegalOccupation(Boolean illegalOccupation) {
		this.illegalOccupation = illegalOccupation;
	}

	public Boolean getLegalOccupation() {
		return legalOccupation;
	}

	public void setLegalOccupation(Boolean legalOccupation) {
		this.legalOccupation = legalOccupation;
	}

	public Boolean getMedicalOccupation() {
		return medicalOccupation;
	}

	public void setMedicalOccupation(Boolean medicalOccupation) {
		this.medicalOccupation = medicalOccupation;
	}

	public Boolean getScientificOccupation() {
		return scientificOccupation;
	}

	public void setScientificOccupation(Boolean scientificOccupation) {
		this.scientificOccupation = scientificOccupation;
	}

	public Boolean getSportsOccupation() {
		return sportsOccupation;
	}

	public void setSportsOccupation(Boolean sportsOccupation) {
		this.sportsOccupation = sportsOccupation;
	}

	public Boolean getVictualOccupation() {
		return victualOccupation;
	}

	public void setVictualOccupation(Boolean victualOccupation) {
		this.victualOccupation = victualOccupation;
	}

}
