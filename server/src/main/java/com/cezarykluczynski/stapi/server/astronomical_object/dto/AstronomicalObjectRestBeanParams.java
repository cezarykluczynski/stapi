package com.cezarykluczynski.stapi.server.astronomical_object.dto;

import com.cezarykluczynski.stapi.model.astronomical_object.entity.enums.AstronomicalObjectType;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class AstronomicalObjectRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

	@FormParam("astronomicalObjectType")
	private AstronomicalObjectType astronomicalObjectType;

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

	public AstronomicalObjectType getAstronomicalObjectType() {
		return astronomicalObjectType;
	}

	public String getLocationUid() {
		return locationUid;
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
