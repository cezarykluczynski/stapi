package com.cezarykluczynski.stapi.client.api.dto;

public class ConflictSearchCriteria extends AbstractPageSortBaseCriteria {

	private String name;

	private Integer yearFrom;

	private Integer yearTo;

	private Boolean earthConflict;

	private Boolean federationWar;

	private Boolean klingonWar;

	private Boolean dominionWarBattle;

	private Boolean alternateReality;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getYearFrom() {
		return yearFrom;
	}

	public void setYearFrom(Integer yearFrom) {
		this.yearFrom = yearFrom;
	}

	public Integer getYearTo() {
		return yearTo;
	}

	public void setYearTo(Integer yearTo) {
		this.yearTo = yearTo;
	}

	public Boolean getEarthConflict() {
		return earthConflict;
	}

	public void setEarthConflict(Boolean earthConflict) {
		this.earthConflict = earthConflict;
	}

	public Boolean getFederationWar() {
		return federationWar;
	}

	public void setFederationWar(Boolean federationWar) {
		this.federationWar = federationWar;
	}

	public Boolean getKlingonWar() {
		return klingonWar;
	}

	public void setKlingonWar(Boolean klingonWar) {
		this.klingonWar = klingonWar;
	}

	public Boolean getDominionWarBattle() {
		return dominionWarBattle;
	}

	public void setDominionWarBattle(Boolean dominionWarBattle) {
		this.dominionWarBattle = dominionWarBattle;
	}

	public Boolean getAlternateReality() {
		return alternateReality;
	}

	public void setAlternateReality(Boolean alternateReality) {
		this.alternateReality = alternateReality;
	}

}
