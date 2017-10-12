package com.cezarykluczynski.stapi.server.conflict.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class ConflictRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

	@FormParam("yearFrom")
	private Integer yearFrom;

	@FormParam("yearTo")
	private Integer yearTo;

	@FormParam("earthConflict")
	private Boolean earthConflict;

	@FormParam("federationWar")
	private Boolean federationWar;

	@FormParam("klingonWar")
	private Boolean klingonWar;

	@FormParam("dominionWarBattle")
	private Boolean dominionWarBattle;

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

	public Integer getYearFrom() {
		return yearFrom;
	}

	public Integer getYearTo() {
		return yearTo;
	}

	public Boolean getEarthConflict() {
		return earthConflict;
	}

	public Boolean getFederationWar() {
		return federationWar;
	}

	public Boolean getKlingonWar() {
		return klingonWar;
	}

	public Boolean getDominionWarBattle() {
		return dominionWarBattle;
	}

	public Boolean getAlternateReality() {
		return alternateReality;
	}

	public static ConflictRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		ConflictRestBeanParams conflictRestBeanParams = new ConflictRestBeanParams();
		conflictRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		conflictRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		conflictRestBeanParams.setSort(pageSortBeanParams.getSort());
		return conflictRestBeanParams;
	}

}
