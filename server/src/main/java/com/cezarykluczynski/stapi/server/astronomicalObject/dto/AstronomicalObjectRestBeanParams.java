package com.cezarykluczynski.stapi.server.astronomicalObject.dto;

import com.cezarykluczynski.stapi.model.astronomicalObject.entity.enums.AstronomicalObjectType;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class AstronomicalObjectRestBeanParams extends PageSortBeanParams {

	private String guid;

	@FormParam("name")
	private String name;

	@FormParam("astronomicalObjectType")
	private AstronomicalObjectType astronomicalObjectType;

	@FormParam("locationGuid")
	private String locationGuid;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getName() {
		return name;
	}

	public AstronomicalObjectType getAstronomicalObjectType() {
		return astronomicalObjectType;
	}

	public String getLocationGuid() {
		return locationGuid;
	}

	public static AstronomicalObjectRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		AstronomicalObjectRestBeanParams astronomicalObjectRestBeanParams = new AstronomicalObjectRestBeanParams();
		astronomicalObjectRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		astronomicalObjectRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		astronomicalObjectRestBeanParams.setSort(pageSortBeanParams.getSort());
		return astronomicalObjectRestBeanParams;
	}

}
