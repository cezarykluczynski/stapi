package com.cezarykluczynski.stapi.server.element.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import jakarta.ws.rs.FormParam;

public class ElementV2RestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

	@FormParam("symbol")
	private String symbol;

	@FormParam("transuranic")
	private Boolean transuranic;

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

	public Boolean getTransuranic() {
		return transuranic;
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

	public static ElementV2RestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		ElementV2RestBeanParams elementV2RestBeanParams = new ElementV2RestBeanParams();
		elementV2RestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		elementV2RestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		elementV2RestBeanParams.setSort(pageSortBeanParams.getSort());
		return elementV2RestBeanParams;
	}

}
