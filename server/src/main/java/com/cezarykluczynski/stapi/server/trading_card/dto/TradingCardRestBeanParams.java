package com.cezarykluczynski.stapi.server.trading_card.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class TradingCardRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

	@FormParam("tradingCardDeckUid")
	private String tradingCardDeckUid;

	@FormParam("tradingCardSetUid")
	private String tradingCardSetUid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public String getTradingCardDeckUid() {
		return tradingCardDeckUid;
	}

	public String getTradingCardSetUid() {
		return tradingCardSetUid;
	}

	public static TradingCardRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		TradingCardRestBeanParams tradingCardRestBeanParams = new TradingCardRestBeanParams();
		tradingCardRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		tradingCardRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		tradingCardRestBeanParams.setSort(pageSortBeanParams.getSort());
		return tradingCardRestBeanParams;
	}

}
