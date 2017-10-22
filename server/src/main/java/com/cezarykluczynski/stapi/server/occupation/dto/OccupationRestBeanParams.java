package com.cezarykluczynski.stapi.server.occupation.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class OccupationRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

	@FormParam("legalOccupation")
	private Boolean legalOccupation;

	@FormParam("medicalOccupation")
	private Boolean medicalOccupation;

	@FormParam("scientificOccupation")
	private Boolean scientificOccupation;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public Boolean getLegalOccupation() {
		return legalOccupation;
	}

	public Boolean getMedicalOccupation() {
		return medicalOccupation;
	}

	public Boolean getScientificOccupation() {
		return scientificOccupation;
	}

	public static OccupationRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		OccupationRestBeanParams occupationRestBeanParams = new OccupationRestBeanParams();
		occupationRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		occupationRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		occupationRestBeanParams.setSort(pageSortBeanParams.getSort());
		return occupationRestBeanParams;
	}

}
