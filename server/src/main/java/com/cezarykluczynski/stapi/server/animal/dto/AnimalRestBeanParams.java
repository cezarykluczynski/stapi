package com.cezarykluczynski.stapi.server.animal.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class AnimalRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

	@FormParam("earthAnimal")
	private Boolean earthAnimal;

	@FormParam("earthInsect")
	private Boolean earthInsect;

	@FormParam("avian")
	private Boolean avian;

	@FormParam("canine")
	private Boolean canine;

	@FormParam("feline")
	private Boolean feline;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public Boolean getEarthAnimal() {
		return earthAnimal;
	}

	public Boolean getEarthInsect() {
		return earthInsect;
	}

	public Boolean getAvian() {
		return avian;
	}

	public Boolean getCanine() {
		return canine;
	}

	public Boolean getFeline() {
		return feline;
	}

	public static AnimalRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		AnimalRestBeanParams animalRestBeanParams = new AnimalRestBeanParams();
		animalRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		animalRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		animalRestBeanParams.setSort(pageSortBeanParams.getSort());
		return animalRestBeanParams;
	}

}
