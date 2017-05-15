package com.cezarykluczynski.stapi.contract.documentation.dto;

import com.cezarykluczynski.stapi.contract.documentation.dto.enums.DocumentType;
import lombok.Data;

@Data
public class DocumentDTO {

	private DocumentType type;

	private String path;

	private String content;

}
