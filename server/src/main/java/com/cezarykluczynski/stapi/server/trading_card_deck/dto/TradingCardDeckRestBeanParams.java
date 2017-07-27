package com.cezarykluczynski.stapi.server.trading_card_deck.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class TradingCardDeckRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

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

	public String getTradingCardSetUid() {
		return tradingCardSetUid;
	}

	public static TradingCardDeckRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		TradingCardDeckRestBeanParams tradingCardDeckRestBeanParams = new TradingCardDeckRestBeanParams();
		tradingCardDeckRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		tradingCardDeckRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		tradingCardDeckRestBeanParams.setSort(pageSortBeanParams.getSort());
		return tradingCardDeckRestBeanParams;
	}

}
