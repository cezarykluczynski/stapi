package com.cezarykluczynski.stapi.server.common.documentation.dto;

import com.cezarykluczynski.stapi.contract.documentation.dto.EndpointDocumentationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentationDTO {

	List<EndpointDocumentationDTO> endpointDocumentations;

}
