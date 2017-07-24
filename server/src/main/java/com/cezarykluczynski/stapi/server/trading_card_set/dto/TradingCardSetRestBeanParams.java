package com.cezarykluczynski.stapi.server.trading_card_set.dto;

import com.cezarykluczynski.stapi.model.trading_card_set.entity.enums.ProductionRunUnit;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class TradingCardSetRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

	@FormParam("releaseYearFrom")
	private Integer releaseYearFrom;

	@FormParam("releaseYearTo")
	private Integer releaseYearTo;

	@FormParam("cardsPerPackFrom")
	private Integer cardsPerPackFrom;

	@FormParam("cardsPerPackTo")
	private Integer cardsPerPackTo;

	@FormParam("packsPerBoxFrom")
	private Integer packsPerBoxFrom;

	@FormParam("packsPerBoxTo")
	private Integer packsPerBoxTo;

	@FormParam("boxesPerCaseFrom")
	private Integer boxesPerCaseFrom;

	@FormParam("boxesPerCaseTo")
	private Integer boxesPerCaseTo;

	@FormParam("productionRunFrom")
	private Integer productionRunFrom;

	@FormParam("productionRunTo")
	private Integer productionRunTo;

	@FormParam("productionRunUnit")
	private ProductionRunUnit productionRunUnit;

	@FormParam("cardWidthFrom")
	private Double cardWidthFrom;

	@FormParam("cardWidthTo")
	private Double cardWidthTo;

	@FormParam("cardHeightFrom")
	private Double cardHeightFrom;

	@FormParam("cardHeightTo")
	private Double cardHeightTo;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public Integer getReleaseYearFrom() {
		return releaseYearFrom;
	}

	public Integer getReleaseYearTo() {
		return releaseYearTo;
	}

	public Integer getCardsPerPackFrom() {
		return cardsPerPackFrom;
	}

	public Integer getCardsPerPackTo() {
		return cardsPerPackTo;
	}

	public Integer getPacksPerBoxFrom() {
		return packsPerBoxFrom;
	}

	public Integer getPacksPerBoxTo() {
		return packsPerBoxTo;
	}

	public Integer getBoxesPerCaseFrom() {
		return boxesPerCaseFrom;
	}

	public Integer getBoxesPerCaseTo() {
		return boxesPerCaseTo;
	}

	public Integer getProductionRunFrom() {
		return productionRunFrom;
	}

	public Integer getProductionRunTo() {
		return productionRunTo;
	}

	public ProductionRunUnit getProductionRunUnit() {
		return productionRunUnit;
	}

	public Double getCardWidthFrom() {
		return cardWidthFrom;
	}

	public Double getCardWidthTo() {
		return cardWidthTo;
	}

	public Double getCardHeightFrom() {
		return cardHeightFrom;
	}

	public Double getCardHeightTo() {
		return cardHeightTo;
	}

	public static TradingCardSetRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		TradingCardSetRestBeanParams tradingCardSetRestBeanParams = new TradingCardSetRestBeanParams();
		tradingCardSetRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		tradingCardSetRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		tradingCardSetRestBeanParams.setSort(pageSortBeanParams.getSort());
		return tradingCardSetRestBeanParams;
	}

}
