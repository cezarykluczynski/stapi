package com.cezarykluczynski.stapi.client.api.dto;

public class MedicalConditionSearchCriteria extends AbstractPageSortBaseCriteria {

	private String name;

	private Boolean psychologicalCondition;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getPsychologicalCondition() {
		return psychologicalCondition;
	}

	public void setPsychologicalCondition(Boolean psychologicalCondition) {
		this.psychologicalCondition = psychologicalCondition;
	}

}
