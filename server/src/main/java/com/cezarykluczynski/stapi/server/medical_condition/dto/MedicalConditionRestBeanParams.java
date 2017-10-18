package com.cezarykluczynski.stapi.server.medical_condition.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class MedicalConditionRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

	@FormParam("psychologicalCondition")
	private Boolean psychologicalCondition;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public Boolean getPsychologicalCondition() {
		return psychologicalCondition;
	}

	public static MedicalConditionRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		MedicalConditionRestBeanParams medicalConditionRestBeanParams = new MedicalConditionRestBeanParams();
		medicalConditionRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		medicalConditionRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		medicalConditionRestBeanParams.setSort(pageSortBeanParams.getSort());
		return medicalConditionRestBeanParams;
	}

}
