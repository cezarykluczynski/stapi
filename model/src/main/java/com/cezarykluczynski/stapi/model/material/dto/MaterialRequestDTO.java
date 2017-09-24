package com.cezarykluczynski.stapi.model.material.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class MaterialRequestDTO {

	private String uid;

	private String name;

	private Boolean chemicalCompound;

	private Boolean biochemicalCompound;

	private Boolean drug;

	private Boolean poisonousSubstance;

	private Boolean explosive;

	private Boolean gemstone;

	private Boolean alloyOrComposite;

	private Boolean fuel;

	private Boolean mineral;

	private Boolean preciousMaterial;

	private RequestSortDTO sort;

}
