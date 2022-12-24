package com.cezarykluczynski.stapi.client.api.dto;

import java.time.LocalDate;

public class MovieSearchCriteria extends AbstractPageSortBaseCriteria {

	private String title;

	private Float stardateFrom;

	private Float stardateTo;

	private Integer yearFrom;

	private Integer yearTo;

	private LocalDate usReleaseDateFrom;

	private LocalDate usReleaseDateTo;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public LocalDate getUsReleaseDateFrom() {
		return usReleaseDateFrom;
	}

	public void setUsReleaseDateFrom(LocalDate usReleaseDateFrom) {
		this.usReleaseDateFrom = usReleaseDateFrom;
	}

	public LocalDate getUsReleaseDateTo() {
		return usReleaseDateTo;
	}

	public void setUsReleaseDateTo(LocalDate usReleaseDateTo) {
		this.usReleaseDateTo = usReleaseDateTo;
	}

}
