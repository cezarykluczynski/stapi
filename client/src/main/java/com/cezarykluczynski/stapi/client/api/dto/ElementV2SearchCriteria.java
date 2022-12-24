package com.cezarykluczynski.stapi.client.api.dto;

public class ElementV2SearchCriteria extends AbstractPageSortBaseCriteria {

	private String name;

	private String symbol;

	private Boolean transuranic;

	private Boolean gammaSeries;

	private Boolean hypersonicSeries;

	private Boolean megaSeries;

	private Boolean omegaSeries;

	private Boolean transonicSeries;

	private Boolean worldSeries;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Boolean getTransuranic() {
		return transuranic;
	}

	public void setTransuranic(Boolean transuranic) {
		this.transuranic = transuranic;
	}

	public Boolean getGammaSeries() {
		return gammaSeries;
	}

	public void setGammaSeries(Boolean gammaSeries) {
		this.gammaSeries = gammaSeries;
	}

	public Boolean getHypersonicSeries() {
		return hypersonicSeries;
	}

	public void setHypersonicSeries(Boolean hypersonicSeries) {
		this.hypersonicSeries = hypersonicSeries;
	}

	public Boolean getMegaSeries() {
		return megaSeries;
	}

	public void setMegaSeries(Boolean megaSeries) {
		this.megaSeries = megaSeries;
	}

	public Boolean getOmegaSeries() {
		return omegaSeries;
	}

	public void setOmegaSeries(Boolean omegaSeries) {
		this.omegaSeries = omegaSeries;
	}

	public Boolean getTransonicSeries() {
		return transonicSeries;
	}

	public void setTransonicSeries(Boolean transonicSeries) {
		this.transonicSeries = transonicSeries;
	}

	public Boolean getWorldSeries() {
		return worldSeries;
	}

	public void setWorldSeries(Boolean worldSeries) {
		this.worldSeries = worldSeries;
	}

}
