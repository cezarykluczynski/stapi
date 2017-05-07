package com.cezarykluczynski.stapi.server.common.dto;

import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;

public class RestEndpointDetailDTO {

	private String name;

	private TrackedEntityType type;

	private String singularName;

	private String pluralName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TrackedEntityType getType() {
		return type;
	}

	public void setType(TrackedEntityType type) {
		this.type = type;
	}

	public String getSingularName() {
		return singularName;
	}

	public void setSingularName(String singularName) {
		this.singularName = singularName;
	}

	public String getPluralName() {
		return pluralName;
	}

	public void setPluralName(String pluralName) {
		this.pluralName = pluralName;
	}

}
