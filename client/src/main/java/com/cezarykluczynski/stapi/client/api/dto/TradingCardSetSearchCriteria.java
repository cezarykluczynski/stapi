package com.cezarykluczynski.stapi.client.api.dto;

import com.cezarykluczynski.stapi.client.v1.rest.model.ProductionRunUnit;

@SuppressWarnings("MethodCount")
public class TradingCardSetSearchCriteria extends AbstractPageSortBaseCriteria {

	private String name;

	private Integer releaseYearFrom;

	private Integer releaseYearTo;

	private Integer cardsPerPackFrom;

	private Integer cardsPerPackTo;

	private Integer packsPerBoxFrom;

	private Integer packsPerBoxTo;

	private Integer boxesPerCaseFrom;

	private Integer boxesPerCaseTo;

	private Integer productionRunFrom;

	private Integer productionRunTo;

	private ProductionRunUnit productionRunUnit;

	private Double cardWidthFrom;

	private Double cardWidthTo;

	private Double cardHeightFrom;

	private Double cardHeightTo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getReleaseYearFrom() {
		return releaseYearFrom;
	}

	public void setReleaseYearFrom(Integer releaseYearFrom) {
		this.releaseYearFrom = releaseYearFrom;
	}

	public Integer getReleaseYearTo() {
		return releaseYearTo;
	}

	public void setReleaseYearTo(Integer releaseYearTo) {
		this.releaseYearTo = releaseYearTo;
	}

	public Integer getCardsPerPackFrom() {
		return cardsPerPackFrom;
	}

	public void setCardsPerPackFrom(Integer cardsPerPackFrom) {
		this.cardsPerPackFrom = cardsPerPackFrom;
	}

	public Integer getCardsPerPackTo() {
		return cardsPerPackTo;
	}

	public void setCardsPerPackTo(Integer cardsPerPackTo) {
		this.cardsPerPackTo = cardsPerPackTo;
	}

	public Integer getPacksPerBoxFrom() {
		return packsPerBoxFrom;
	}

	public void setPacksPerBoxFrom(Integer packsPerBoxFrom) {
		this.packsPerBoxFrom = packsPerBoxFrom;
	}

	public Integer getPacksPerBoxTo() {
		return packsPerBoxTo;
	}

	public void setPacksPerBoxTo(Integer packsPerBoxTo) {
		this.packsPerBoxTo = packsPerBoxTo;
	}

	public Integer getBoxesPerCaseFrom() {
		return boxesPerCaseFrom;
	}

	public void setBoxesPerCaseFrom(Integer boxesPerCaseFrom) {
		this.boxesPerCaseFrom = boxesPerCaseFrom;
	}

	public Integer getBoxesPerCaseTo() {
		return boxesPerCaseTo;
	}

	public void setBoxesPerCaseTo(Integer boxesPerCaseTo) {
		this.boxesPerCaseTo = boxesPerCaseTo;
	}

	public Integer getProductionRunFrom() {
		return productionRunFrom;
	}

	public void setProductionRunFrom(Integer productionRunFrom) {
		this.productionRunFrom = productionRunFrom;
	}

	public Integer getProductionRunTo() {
		return productionRunTo;
	}

	public void setProductionRunTo(Integer productionRunTo) {
		this.productionRunTo = productionRunTo;
	}

	public ProductionRunUnit getProductionRunUnit() {
		return productionRunUnit;
	}

	public void setProductionRunUnit(ProductionRunUnit productionRunUnit) {
		this.productionRunUnit = productionRunUnit;
	}

	public Double getCardWidthFrom() {
		return cardWidthFrom;
	}

	public void setCardWidthFrom(Double cardWidthFrom) {
		this.cardWidthFrom = cardWidthFrom;
	}

	public Double getCardWidthTo() {
		return cardWidthTo;
	}

	public void setCardWidthTo(Double cardWidthTo) {
		this.cardWidthTo = cardWidthTo;
	}

	public Double getCardHeightFrom() {
		return cardHeightFrom;
	}

	public void setCardHeightFrom(Double cardHeightFrom) {
		this.cardHeightFrom = cardHeightFrom;
	}

	public Double getCardHeightTo() {
		return cardHeightTo;
	}

	public void setCardHeightTo(Double cardHeightTo) {
		this.cardHeightTo = cardHeightTo;
	}

}
