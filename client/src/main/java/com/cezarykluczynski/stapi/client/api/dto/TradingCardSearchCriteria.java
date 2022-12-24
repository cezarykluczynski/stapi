package com.cezarykluczynski.stapi.client.api.dto;

public class TradingCardSearchCriteria extends AbstractPageSortBaseCriteria {

	private String name;

	private String tradingCardDeckUid;

	private String tradingCardSetUid;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTradingCardDeckUid() {
		return tradingCardDeckUid;
	}

	public void setTradingCardDeckUid(String tradingCardDeckUid) {
		this.tradingCardDeckUid = tradingCardDeckUid;
	}

	public String getTradingCardSetUid() {
		return tradingCardSetUid;
	}

	public void setTradingCardSetUid(String tradingCardSetUid) {
		this.tradingCardSetUid = tradingCardSetUid;
	}

}
