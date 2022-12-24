package com.cezarykluczynski.stapi.client.api.dto;

public class ComicSeriesSearchCriteria extends AbstractPageSortBaseCriteria {

	private String title;

	private Integer publishedYearFrom;

	private Integer publishedYearTo;

	private Integer numberOfIssuesFrom;

	private Integer numberOfIssuesTo;

	private Float stardateFrom;

	private Float stardateTo;

	private Integer yearFrom;

	private Integer yearTo;

	private Boolean miniseries;

	private Boolean photonovelSeries;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPublishedYearFrom() {
		return publishedYearFrom;
	}

	public void setPublishedYearFrom(Integer publishedYearFrom) {
		this.publishedYearFrom = publishedYearFrom;
	}

	public Integer getPublishedYearTo() {
		return publishedYearTo;
	}

	public void setPublishedYearTo(Integer publishedYearTo) {
		this.publishedYearTo = publishedYearTo;
	}

	public Integer getNumberOfIssuesFrom() {
		return numberOfIssuesFrom;
	}

	public void setNumberOfIssuesFrom(Integer numberOfIssuesFrom) {
		this.numberOfIssuesFrom = numberOfIssuesFrom;
	}

	public Integer getNumberOfIssuesTo() {
		return numberOfIssuesTo;
	}

	public void setNumberOfIssuesTo(Integer numberOfIssuesTo) {
		this.numberOfIssuesTo = numberOfIssuesTo;
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

	public Boolean getMiniseries() {
		return miniseries;
	}

	public void setMiniseries(Boolean miniseries) {
		this.miniseries = miniseries;
	}

	public Boolean getPhotonovelSeries() {
		return photonovelSeries;
	}

	public void setPhotonovelSeries(Boolean photonovelSeries) {
		this.photonovelSeries = photonovelSeries;
	}

}
