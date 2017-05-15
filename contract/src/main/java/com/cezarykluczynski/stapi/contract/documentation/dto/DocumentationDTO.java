package com.cezarykluczynski.stapi.contract.documentation.dto;

import lombok.Data;

import java.util.List;

@Data
public class DocumentationDTO {

	List<DocumentDTO> restDocuments;

	List<DocumentDTO> soapDocuments;

}
