package com.cezarykluczynski.stapi.client.api.dto;

public class FoodSearchCriteria extends AbstractPageSortBaseCriteria {

	private String name;

	private Boolean earthlyOrigin;

	private Boolean dessert;

	private Boolean fruit;

	private Boolean herbOrSpice;

	private Boolean sauce;

	private Boolean soup;

	private Boolean beverage;

	private Boolean alcoholicBeverage;

	private Boolean juice;

	private Boolean tea;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getEarthlyOrigin() {
		return earthlyOrigin;
	}

	public void setEarthlyOrigin(Boolean earthlyOrigin) {
		this.earthlyOrigin = earthlyOrigin;
	}

	public Boolean getDessert() {
		return dessert;
	}

	public void setDessert(Boolean dessert) {
		this.dessert = dessert;
	}

	public Boolean getFruit() {
		return fruit;
	}

	public void setFruit(Boolean fruit) {
		this.fruit = fruit;
	}

	public Boolean getHerbOrSpice() {
		return herbOrSpice;
	}

	public void setHerbOrSpice(Boolean herbOrSpice) {
		this.herbOrSpice = herbOrSpice;
	}

	public Boolean getSauce() {
		return sauce;
	}

	public void setSauce(Boolean sauce) {
		this.sauce = sauce;
	}

	public Boolean getSoup() {
		return soup;
	}

	public void setSoup(Boolean soup) {
		this.soup = soup;
	}

	public Boolean getBeverage() {
		return beverage;
	}

	public void setBeverage(Boolean beverage) {
		this.beverage = beverage;
	}

	public Boolean getAlcoholicBeverage() {
		return alcoholicBeverage;
	}

	public void setAlcoholicBeverage(Boolean alcoholicBeverage) {
		this.alcoholicBeverage = alcoholicBeverage;
	}

	public Boolean getJuice() {
		return juice;
	}

	public void setJuice(Boolean juice) {
		this.juice = juice;
	}

	public Boolean getTea() {
		return tea;
	}

	public void setTea(Boolean tea) {
		this.tea = tea;
	}

}
