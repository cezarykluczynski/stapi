package com.cezarykluczynski.stapi.client.api.dto;

public class MagazineSearchCriteria extends AbstractPageSortBaseCriteria {

	private String title;

	private Integer publishedYearFrom;

	private Integer publishedYearTo;

	private Integer numberOfPagesFrom;

	private Integer numberOfPagesTo;

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

	public Integer getNumberOfPagesFrom() {
		return numberOfPagesFrom;
	}

	public void setNumberOfPagesFrom(Integer numberOfPagesFrom) {
		this.numberOfPagesFrom = numberOfPagesFrom;
	}

	public Integer getNumberOfPagesTo() {
		return numberOfPagesTo;
	}

	public void setNumberOfPagesTo(Integer numberOfPagesTo) {
		this.numberOfPagesTo = numberOfPagesTo;
	}

}
