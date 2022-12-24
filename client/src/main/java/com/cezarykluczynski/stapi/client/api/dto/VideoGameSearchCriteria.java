package com.cezarykluczynski.stapi.client.api.dto;

import java.time.LocalDate;

public class VideoGameSearchCriteria extends AbstractPageSortBaseCriteria {

	private String title;

	private LocalDate releaseDateFrom;

	private LocalDate releaseDateTo;

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

}
