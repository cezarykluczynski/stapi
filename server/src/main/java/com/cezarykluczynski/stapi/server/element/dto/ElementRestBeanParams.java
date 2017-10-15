package com.cezarykluczynski.stapi.server.element.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class ElementRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

	@FormParam("symbol")
	private String symbol;

	@FormParam("transuranium")
	private Boolean transuranium;

	@FormParam("gammaSeries")
	private Boolean gammaSeries;

	@FormParam("hypersonicSeries")
	private Boolean hypersonicSeries;

	@FormParam("megaSeries")
	private Boolean megaSeries;

	@FormParam("omegaSeries")
	private Boolean omegaSeries;

	@FormParam("transonicSeries")
	private Boolean transonicSeries;

	@FormParam("worldSeries")
	private Boolean worldSeries;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public String getSymbol() {
		return symbol;
	}

	public Boolean getTransuranium() {
		return transuranium;
	}

	public Boolean getGammaSeries() {
		return gammaSeries;
	}

	public Boolean getHypersonicSeries() {
		return hypersonicSeries;
	}

	public Boolean getMegaSeries() {
		return megaSeries;
	}

	public Boolean getOmegaSeries() {
		return omegaSeries;
	}

	public Boolean getTransonicSeries() {
		return transonicSeries;
	}

	public Boolean getWorldSeries() {
		return worldSeries;
	}

	public static ElementRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		ElementRestBeanParams elementRestBeanParams = new ElementRestBeanParams();
		elementRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		elementRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		elementRestBeanParams.setSort(pageSortBeanParams.getSort());
		return elementRestBeanParams;
	}

}
