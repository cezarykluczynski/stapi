package com.cezarykluczynski.stapi.model.literature.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class LiteratureRequestDTO {

	private String uid;

	private String title;

	private Boolean earthlyOrigin;

	private Boolean shakespeareanWork;

	private Boolean report;

	private Boolean scientificLiterature;

	private Boolean technicalManual;

	private Boolean religiousLiterature;

	private RequestSortDTO sort;

}
