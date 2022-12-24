package com.cezarykluczynski.stapi.client.api.dto;

public class SpacecraftV2SearchCriteria extends AbstractPageSortBaseCriteria {

	private String name;

	private String registry;

	private String status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegistry() {
		return registry;
	}

	public void setRegistry(String registry) {
		this.registry = registry;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
