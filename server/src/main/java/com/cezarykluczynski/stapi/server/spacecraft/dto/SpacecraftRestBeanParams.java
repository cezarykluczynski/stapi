package com.cezarykluczynski.stapi.server.spacecraft.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class SpacecraftRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public static SpacecraftRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		SpacecraftRestBeanParams spacecraftRestBeanParams = new SpacecraftRestBeanParams();
		spacecraftRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		spacecraftRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		spacecraftRestBeanParams.setSort(pageSortBeanParams.getSort());
		return spacecraftRestBeanParams;
	}

}
