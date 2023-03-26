package com.cezarykluczynski.stapi.server.astronomical_object.dto;

import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2Type;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import jakarta.ws.rs.FormParam;

public class AstronomicalObjectV2RestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

	@FormParam("astronomicalObjectType")
	private AstronomicalObjectV2Type astronomicalObjectType;

	@FormParam("locationUid")
	private String locationUid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public AstronomicalObjectV2Type getAstronomicalObjectType() {
		return astronomicalObjectType;
	}

	public String getLocationUid() {
		return locationUid;
	}

	public static AstronomicalObjectV2RestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		AstronomicalObjectV2RestBeanParams astronomicalObjectV2RestBeanParams = new AstronomicalObjectV2RestBeanParams();
		astronomicalObjectV2RestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		astronomicalObjectV2RestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		astronomicalObjectV2RestBeanParams.setSort(pageSortBeanParams.getSort());
		return astronomicalObjectV2RestBeanParams;
	}

}
