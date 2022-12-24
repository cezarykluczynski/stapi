package com.cezarykluczynski.stapi.client.api.dto;

import java.time.LocalDate;

public class EpisodeSearchCriteria extends AbstractPageSortBaseCriteria {

	private String title;

	private Integer seasonNumberFrom;

	private Integer seasonNumberTo;

	private Integer episodeNumberFrom;

	private Integer episodeNumberTo;

	private String productionSerialNumber;

	private Boolean featureLength;

	private Float stardateFrom;

	private Float stardateTo;

	private Integer yearFrom;

	private Integer yearTo;

	private LocalDate usAirDateFrom;

	private LocalDate usAirDateTo;

	private LocalDate finalScriptDateFrom;

	private LocalDate finalScriptDateTo;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getSeasonNumberFrom() {
		return seasonNumberFrom;
	}

	public void setSeasonNumberFrom(Integer seasonNumberFrom) {
		this.seasonNumberFrom = seasonNumberFrom;
	}

	public Integer getSeasonNumberTo() {
		return seasonNumberTo;
	}

	public void setSeasonNumberTo(Integer seasonNumberTo) {
		this.seasonNumberTo = seasonNumberTo;
	}

	public Integer getEpisodeNumberFrom() {
		return episodeNumberFrom;
	}

	public void setEpisodeNumberFrom(Integer episodeNumberFrom) {
		this.episodeNumberFrom = episodeNumberFrom;
	}

	public Integer getEpisodeNumberTo() {
		return episodeNumberTo;
	}

	public void setEpisodeNumberTo(Integer episodeNumberTo) {
		this.episodeNumberTo = episodeNumberTo;
	}

	public String getProductionSerialNumber() {
		return productionSerialNumber;
	}

	public void setProductionSerialNumber(String productionSerialNumber) {
		this.productionSerialNumber = productionSerialNumber;
	}

	public Boolean getFeatureLength() {
		return featureLength;
	}

	public void setFeatureLength(Boolean featureLength) {
		this.featureLength = featureLength;
	}

	public Float getStardateFrom() {
		return stardateFrom;
	}

	public void setStardateFrom(Float stardateFrom) {
		this.stardateFrom = stardateFrom;
	}

	public Float getStardateTo() {
		return stardateTo;
	}

	public void setStardateTo(Float stardateTo) {
		this.stardateTo = stardateTo;
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

	public LocalDate getUsAirDateFrom() {
		return usAirDateFrom;
	}

	public void setUsAirDateFrom(LocalDate usAirDateFrom) {
		this.usAirDateFrom = usAirDateFrom;
	}

	public LocalDate getUsAirDateTo() {
		return usAirDateTo;
	}

	public void setUsAirDateTo(LocalDate usAirDateTo) {
		this.usAirDateTo = usAirDateTo;
	}

	public LocalDate getFinalScriptDateFrom() {
		return finalScriptDateFrom;
	}

	public void setFinalScriptDateFrom(LocalDate finalScriptDateFrom) {
		this.finalScriptDateFrom = finalScriptDateFrom;
	}

	public LocalDate getFinalScriptDateTo() {
		return finalScriptDateTo;
	}

	public void setFinalScriptDateTo(LocalDate finalScriptDateTo) {
		this.finalScriptDateTo = finalScriptDateTo;
	}

}
