package com.cezarykluczynski.stapi.server.common.documentation.dto;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.List;

@SuppressFBWarnings({"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
public class DocumentationDTO {

	private List<DocumentDTO> restDocuments;

	private List<DocumentDTO> soapDocuments;

	public List<DocumentDTO> getRestDocuments() {
		return restDocuments;
	}

	public void setRestDocuments(List<DocumentDTO> restDocuments) {
		this.restDocuments = restDocuments;
	}

	public List<DocumentDTO> getSoapDocuments() {
		return soapDocuments;
	}

	public void setSoapDocuments(List<DocumentDTO> soapDocuments) {
		this.soapDocuments = soapDocuments;
	}

}
