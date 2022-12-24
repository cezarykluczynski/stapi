package com.cezarykluczynski.stapi.client.api.dto;

public class BookSeriesSearchCriteria extends AbstractPageSortBaseCriteria {

	private String title;

	private Integer publishedYearFrom;

	private Integer publishedYearTo;

	private Integer numberOfBooksFrom;

	private Integer numberOfBooksTo;

	private Integer yearFrom;

	private Integer yearTo;

	private Boolean miniseries;

	@SuppressWarnings("MemberName")
	private Boolean eBookSeries;

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

	public Integer getNumberOfBooksFrom() {
		return numberOfBooksFrom;
	}

	public void setNumberOfBooksFrom(Integer numberOfBooksFrom) {
		this.numberOfBooksFrom = numberOfBooksFrom;
	}

	public Integer getNumberOfBooksTo() {
		return numberOfBooksTo;
	}

	public void setNumberOfBooksTo(Integer numberOfBooksTo) {
		this.numberOfBooksTo = numberOfBooksTo;
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

	public Boolean getEBookSeries() {
		return eBookSeries;
	}

	public void setEBookSeries(@SuppressWarnings({"ParameterName", "HiddenField"}) Boolean eBookSeries) {
		this.eBookSeries = eBookSeries;
	}

}
