package com.cezarykluczynski.stapi.server.spacecraft_class.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class SpacecraftClassRestBeanParams extends PageSortBeanParams {

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

	public static SpacecraftClassRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		SpacecraftClassRestBeanParams spacecraftClassRestBeanParams = new SpacecraftClassRestBeanParams();
		spacecraftClassRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		spacecraftClassRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		spacecraftClassRestBeanParams.setSort(pageSortBeanParams.getSort());
		return spacecraftClassRestBeanParams;
	}

}
