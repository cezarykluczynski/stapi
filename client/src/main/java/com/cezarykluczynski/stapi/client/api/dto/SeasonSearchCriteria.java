package com.cezarykluczynski.stapi.client.api.dto;

public class SeasonSearchCriteria extends AbstractPageSortBaseCriteria {

	private String title;

	private Integer seasonNumberFrom;

	private Integer seasonNumberTo;

	private Integer numberOfEpisodesFrom;

	private Integer numberOfEpisodesTo;

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

	public Integer getNumberOfEpisodesFrom() {
		return numberOfEpisodesFrom;
	}

	public void setNumberOfEpisodesFrom(Integer numberOfEpisodesFrom) {
		this.numberOfEpisodesFrom = numberOfEpisodesFrom;
	}

	public Integer getNumberOfEpisodesTo() {
		return numberOfEpisodesTo;
	}

	public void setNumberOfEpisodesTo(Integer numberOfEpisodesTo) {
		this.numberOfEpisodesTo = numberOfEpisodesTo;
	}

}
