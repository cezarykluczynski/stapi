package com.cezarykluczynski.stapi.server.title.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import jakarta.ws.rs.FormParam;

public class TitleV2RestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

	@FormParam("militaryRank")
	private Boolean militaryRank;

	@FormParam("fleetRank")
	private Boolean fleetRank;

	@FormParam("religiousTitle")
	private Boolean religiousTitle;

	@FormParam("educationTitle")
	private Boolean educationTitle;

	@FormParam("mirror")
	private Boolean mirror;

	public Boolean getMilitaryRank() {
		return militaryRank;
	}

	public Boolean getFleetRank() {
		return fleetRank;
	}

	public Boolean getReligiousTitle() {
		return religiousTitle;
	}

	public Boolean getEducationTitle() {
		return educationTitle;
	}

	public Boolean getMirror() {
		return mirror;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}


	public static TitleV2RestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		TitleV2RestBeanParams titleRestBeanParams = new TitleV2RestBeanParams();
		titleRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		titleRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		titleRestBeanParams.setSort(pageSortBeanParams.getSort());
		return titleRestBeanParams;
	}

}
