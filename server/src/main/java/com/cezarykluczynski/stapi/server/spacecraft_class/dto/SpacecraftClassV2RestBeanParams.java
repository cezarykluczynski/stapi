package com.cezarykluczynski.stapi.server.spacecraft_class.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import jakarta.ws.rs.FormParam;

public class SpacecraftClassV2RestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

	@FormParam("warpCapable")
	private Boolean warpCapable;

	@FormParam("mirror")
	private Boolean mirror;

	@FormParam("alternateReality")
	private Boolean alternateReality;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public Boolean getWarpCapable() {
		return warpCapable;
	}

	public Boolean getMirror() {
		return mirror;
	}

	public Boolean getAlternateReality() {
		return alternateReality;
	}

	public static SpacecraftClassV2RestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		SpacecraftClassV2RestBeanParams spacecraftClassV2RestBeanParams = new SpacecraftClassV2RestBeanParams();
		spacecraftClassV2RestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		spacecraftClassV2RestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		spacecraftClassV2RestBeanParams.setSort(pageSortBeanParams.getSort());
		return spacecraftClassV2RestBeanParams;
	}

}
