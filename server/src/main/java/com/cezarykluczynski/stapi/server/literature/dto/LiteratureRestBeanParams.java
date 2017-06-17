package com.cezarykluczynski.stapi.server.literature.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class LiteratureRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String title;

	@FormParam("earthlyOrigin")
	private Boolean earthlyOrigin;

	@FormParam("shakespeareanWork")
	private Boolean shakespeareanWork;

	@FormParam("report")
	private Boolean report;

	@FormParam("scientificLiterature")
	private Boolean scientificLiterature;

	@FormParam("technicalManual")
	private Boolean technicalManual;

	@FormParam("religiousLiterature")
	private Boolean religiousLiterature;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getTitle() {
		return title;
	}

	public Boolean getEarthlyOrigin() {
		return earthlyOrigin;
	}

	public Boolean getShakespeareanWork() {
		return shakespeareanWork;
	}

	public Boolean getReport() {
		return report;
	}

	public Boolean getScientificLiterature() {
		return scientificLiterature;
	}

	public Boolean getTechnicalManual() {
		return technicalManual;
	}

	public Boolean getReligiousLiterature() {
		return religiousLiterature;
	}

	public static LiteratureRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		LiteratureRestBeanParams literatureRestBeanParams = new LiteratureRestBeanParams();
		literatureRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		literatureRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		literatureRestBeanParams.setSort(pageSortBeanParams.getSort());
		return literatureRestBeanParams;
	}

}
