package com.cezarykluczynski.stapi.client.api.dto;

import java.time.LocalDate;

public class SoundtrackSearchCriteria extends AbstractPageSortBaseCriteria {

	private String title;

	private LocalDate releaseDateFrom;

	private LocalDate releaseDateTo;

	private Integer lengthFrom;

	private Integer lengthTo;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getReleaseDateFrom() {
		return releaseDateFrom;
	}

	public void setReleaseDateFrom(LocalDate releaseDateFrom) {
		this.releaseDateFrom = releaseDateFrom;
	}

	public LocalDate getReleaseDateTo() {
		return releaseDateTo;
	}

	public void setReleaseDateTo(LocalDate releaseDateTo) {
		this.releaseDateTo = releaseDateTo;
	}

	public Integer getLengthFrom() {
		return lengthFrom;
	}

	public void setLengthFrom(Integer lengthFrom) {
		this.lengthFrom = lengthFrom;
	}

	public Integer getLengthTo() {
		return lengthTo;
	}

	public void setLengthTo(Integer lengthTo) {
		this.lengthTo = lengthTo;
	}

}
