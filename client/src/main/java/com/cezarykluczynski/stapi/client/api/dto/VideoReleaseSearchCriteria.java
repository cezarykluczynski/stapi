package com.cezarykluczynski.stapi.client.api.dto;

public class VideoReleaseSearchCriteria extends AbstractPageSortBaseCriteria {

	private String title;

	private Integer yearFrom;

	private Integer yearTo;

	private Integer runTimeFrom;

	private Integer runTimeTo;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Integer getRunTimeFrom() {
		return runTimeFrom;
	}

	public void setRunTimeFrom(Integer runTimeFrom) {
		this.runTimeFrom = runTimeFrom;
	}

	public Integer getRunTimeTo() {
		return runTimeTo;
	}

	public void setRunTimeTo(Integer runTimeTo) {
		this.runTimeTo = runTimeTo;
	}

}
