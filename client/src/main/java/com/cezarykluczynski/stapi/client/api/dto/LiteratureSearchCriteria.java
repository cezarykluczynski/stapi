package com.cezarykluczynski.stapi.client.api.dto;

public class LiteratureSearchCriteria extends AbstractPageSortBaseCriteria {

	private String title;

	private Boolean earthlyOrigin;

	private Boolean shakespeareanWork;

	private Boolean report;

	private Boolean scientificLiterature;

	private Boolean technicalManual;

	private Boolean religiousLiterature;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getEarthlyOrigin() {
		return earthlyOrigin;
	}

	public void setEarthlyOrigin(Boolean earthlyOrigin) {
		this.earthlyOrigin = earthlyOrigin;
	}

	public Boolean getShakespeareanWork() {
		return shakespeareanWork;
	}

	public void setShakespeareanWork(Boolean shakespeareanWork) {
		this.shakespeareanWork = shakespeareanWork;
	}

	public Boolean getReport() {
		return report;
	}

	public void setReport(Boolean report) {
		this.report = report;
	}

	public Boolean getScientificLiterature() {
		return scientificLiterature;
	}

	public void setScientificLiterature(Boolean scientificLiterature) {
		this.scientificLiterature = scientificLiterature;
	}

	public Boolean getTechnicalManual() {
		return technicalManual;
	}

	public void setTechnicalManual(Boolean technicalManual) {
		this.technicalManual = technicalManual;
	}

	public Boolean getReligiousLiterature() {
		return religiousLiterature;
	}

	public void setReligiousLiterature(Boolean religiousLiterature) {
		this.religiousLiterature = religiousLiterature;
	}

}
