package com.cezarykluczynski.stapi.server.common.documentation.dto;

import com.cezarykluczynski.stapi.server.common.documentation.dto.enums.DocumentType;

public class DocumentDTO {

	private DocumentType type;

	private String path;

	private String content;

	public DocumentType getType() {
		return type;
	}

	public void setType(DocumentType type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
