package com.cezarykluczynski.stapi.server.food.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class FoodRestBeanParams extends PageSortBeanParams {

	private String guid;

	@FormParam("name")
	private String name;

	@FormParam("earthlyOrigin")
	private Boolean earthlyOrigin;

	@FormParam("dessert")
	private Boolean dessert;

	@FormParam("fruit")
	private Boolean fruit;

	@FormParam("herbOrSpice")
	private Boolean herbOrSpice;

	@FormParam("sauce")
	private Boolean sauce;

	@FormParam("soup")
	private Boolean soup;

	@FormParam("beverage")
	private Boolean beverage;

	@FormParam("alcoholicBeverage")
	private Boolean alcoholicBeverage;

	@FormParam("juice")
	private Boolean juice;

	@FormParam("tea")
	private Boolean tea;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getName() {
		return name;
	}

	public Boolean getEarthlyOrigin() {
		return earthlyOrigin;
	}

	public Boolean getDessert() {
		return dessert;
	}

	public Boolean getFruit() {
		return fruit;
	}

	public Boolean getHerbOrSpice() {
		return herbOrSpice;
	}

	public Boolean getSauce() {
		return sauce;
	}

	public Boolean getSoup() {
		return soup;
	}

	public Boolean getBeverage() {
		return beverage;
	}

	public Boolean getAlcoholicBeverage() {
		return alcoholicBeverage;
	}

	public Boolean getJuice() {
		return juice;
	}

	public Boolean getTea() {
		return tea;
	}

	public static FoodRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		FoodRestBeanParams foodRestBeanParams = new FoodRestBeanParams();
		foodRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		foodRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		foodRestBeanParams.setSort(pageSortBeanParams.getSort());
		return foodRestBeanParams;
	}

}
