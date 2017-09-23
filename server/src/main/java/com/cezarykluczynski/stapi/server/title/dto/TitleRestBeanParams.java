package com.cezarykluczynski.stapi.server.title.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class TitleRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

	@FormParam("militaryRank")
	private Boolean militaryRank;

	@FormParam("fleetRank")
	private Boolean fleetRank;

	@FormParam("religiousTitle")
	private Boolean religiousTitle;

	@FormParam("position")
	private Boolean position;

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

	public Boolean getPosition() {
		return position;
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


	public static TitleRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		TitleRestBeanParams titleRestBeanParams = new TitleRestBeanParams();
		titleRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		titleRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		titleRestBeanParams.setSort(pageSortBeanParams.getSort());
		return titleRestBeanParams;
	}

}
