package com.cezarykluczynski.stapi.client.api.dto;

public class MagazineSeriesSearchCriteria extends AbstractPageSortBaseCriteria {

	private String title;

	private Integer publishedYearFrom;

	private Integer publishedYearTo;

	private Integer numberOfIssuesFrom;

	private Integer numberOfIssuesTo;

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

}
