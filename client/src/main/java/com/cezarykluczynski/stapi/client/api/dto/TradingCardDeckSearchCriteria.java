package com.cezarykluczynski.stapi.client.api.dto;

public class TradingCardDeckSearchCriteria extends AbstractPageSortBaseCriteria {

	private String name;

	private String tradingCardSetUid;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTradingCardSetUid() {
		return tradingCardSetUid;
	}

	public void setTradingCardSetUid(String tradingCardSetUid) {
		this.tradingCardSetUid = tradingCardSetUid;
	}

}
