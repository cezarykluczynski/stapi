package com.cezarykluczynski.stapi.client.api.dto;

public class ComicCollectionSearchCriteria extends AbstractPageSortBaseCriteria {

	private String title;

	private Integer publishedYearFrom;

	private Integer publishedYearTo;

	private Integer numberOfPagesFrom;

	private Integer numberOfPagesTo;

	private Float stardateFrom;

	private Float stardateTo;

	private Integer yearFrom;

	private Integer yearTo;

	private Boolean photonovel;

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

	public Boolean getPhotonovel() {
		return photonovel;
	}

	public void setPhotonovel(Boolean photonovel) {
		this.photonovel = photonovel;
	}

}
