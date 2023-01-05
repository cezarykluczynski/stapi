package com.cezarykluczynski.stapi.server.spacecraft.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import jakarta.ws.rs.FormParam;

public class SpacecraftV2RestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

	@FormParam("registry")
	private String registry;

	@FormParam("status")
	private String status;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public String getRegistry() {
		return registry;
	}

	public String getStatus() {
		return status;
	}

	public static SpacecraftV2RestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		SpacecraftV2RestBeanParams spacecraftRestBeanParams = new SpacecraftV2RestBeanParams();
		spacecraftRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		spacecraftRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		spacecraftRestBeanParams.setSort(pageSortBeanParams.getSort());
		return spacecraftRestBeanParams;
	}

}
