package com.cezarykluczynski.stapi.contract.documentation.dto;

import lombok.Data;

import java.util.List;

@Data
public class DocumentationDTO {

	private List<DocumentDTO> restDocuments;

	private List<DocumentDTO> soapDocuments;

}
