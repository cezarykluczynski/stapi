package com.cezarykluczynski.stapi.model.medical_condition.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class MedicalConditionRequestDTO {

	private String uid;

	private String name;

	private Boolean psychologicalCondition;

	private RequestSortDTO sort;
}
