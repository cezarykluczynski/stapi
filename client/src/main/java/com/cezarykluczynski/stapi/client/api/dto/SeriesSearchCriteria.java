package com.cezarykluczynski.stapi.client.api.dto;

import java.time.LocalDate;

public class SeriesSearchCriteria extends AbstractPageSortBaseCriteria {

	private String title;

	private String abbreviation;

	private Integer productionStartYearFrom;

	private Integer productionStartYearTo;

	private Integer productionEndYearFrom;

	private Integer productionEndYearTo;

	private LocalDate originalRunStartDateFrom;

	private LocalDate originalRunStartDateTo;

	private LocalDate originalRunEndDateFrom;

	private LocalDate originalRunEndDateTo;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public Integer getProductionStartYearFrom() {
		return productionStartYearFrom;
	}

	public void setProductionStartYearFrom(Integer productionStartYearFrom) {
		this.productionStartYearFrom = productionStartYearFrom;
	}

	public Integer getProductionStartYearTo() {
		return productionStartYearTo;
	}

	public void setProductionStartYearTo(Integer productionStartYearTo) {
		this.productionStartYearTo = productionStartYearTo;
	}

	public Integer getProductionEndYearFrom() {
		return productionEndYearFrom;
	}

	public void setProductionEndYearFrom(Integer productionEndYearFrom) {
		this.productionEndYearFrom = productionEndYearFrom;
	}

	public Integer getProductionEndYearTo() {
		return productionEndYearTo;
	}

	public void setProductionEndYearTo(Integer productionEndYearTo) {
		this.productionEndYearTo = productionEndYearTo;
	}

	public LocalDate getOriginalRunStartDateFrom() {
		return originalRunStartDateFrom;
	}

	public void setOriginalRunStartDateFrom(LocalDate originalRunStartDateFrom) {
		this.originalRunStartDateFrom = originalRunStartDateFrom;
	}

	public LocalDate getOriginalRunStartDateTo() {
		return originalRunStartDateTo;
	}

	public void setOriginalRunStartDateTo(LocalDate originalRunStartDateTo) {
		this.originalRunStartDateTo = originalRunStartDateTo;
	}

	public LocalDate getOriginalRunEndDateFrom() {
		return originalRunEndDateFrom;
	}

	public void setOriginalRunEndDateFrom(LocalDate originalRunEndDateFrom) {
		this.originalRunEndDateFrom = originalRunEndDateFrom;
	}

	public LocalDate getOriginalRunEndDateTo() {
		return originalRunEndDateTo;
	}

	public void setOriginalRunEndDateTo(LocalDate originalRunEndDateTo) {
		this.originalRunEndDateTo = originalRunEndDateTo;
	}

}
