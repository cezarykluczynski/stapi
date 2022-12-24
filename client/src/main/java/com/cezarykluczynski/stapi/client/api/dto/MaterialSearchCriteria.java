package com.cezarykluczynski.stapi.client.api.dto;

public class MaterialSearchCriteria extends AbstractPageSortBaseCriteria {

	private String name;

	private Boolean chemicalCompound;

	private Boolean biochemicalCompound;

	private Boolean drug;

	private Boolean poisonousSubstance;

	private Boolean explosive;

	private Boolean gemstone;

	private Boolean alloyOrComposite;

	private Boolean fuel;

	private Boolean mineral;

	private Boolean preciousMaterial;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getChemicalCompound() {
		return chemicalCompound;
	}

	public void setChemicalCompound(Boolean chemicalCompound) {
		this.chemicalCompound = chemicalCompound;
	}

	public Boolean getBiochemicalCompound() {
		return biochemicalCompound;
	}

	public void setBiochemicalCompound(Boolean biochemicalCompound) {
		this.biochemicalCompound = biochemicalCompound;
	}

	public Boolean getDrug() {
		return drug;
	}

	public void setDrug(Boolean drug) {
		this.drug = drug;
	}

	public Boolean getPoisonousSubstance() {
		return poisonousSubstance;
	}

	public void setPoisonousSubstance(Boolean poisonousSubstance) {
		this.poisonousSubstance = poisonousSubstance;
	}

	public Boolean getExplosive() {
		return explosive;
	}

	public void setExplosive(Boolean explosive) {
		this.explosive = explosive;
	}

	public Boolean getGemstone() {
		return gemstone;
	}

	public void setGemstone(Boolean gemstone) {
		this.gemstone = gemstone;
	}

	public Boolean getAlloyOrComposite() {
		return alloyOrComposite;
	}

	public void setAlloyOrComposite(Boolean alloyOrComposite) {
		this.alloyOrComposite = alloyOrComposite;
	}

	public Boolean getFuel() {
		return fuel;
	}

	public void setFuel(Boolean fuel) {
		this.fuel = fuel;
	}

	public Boolean getMineral() {
		return mineral;
	}

	public void setMineral(Boolean mineral) {
		this.mineral = mineral;
	}

	public Boolean getPreciousMaterial() {
		return preciousMaterial;
	}

	public void setPreciousMaterial(Boolean preciousMaterial) {
		this.preciousMaterial = preciousMaterial;
	}

}
