package com.cezarykluczynski.stapi.model.company.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class CompanyRequestDTO {

	private String uid;

	private String name;

	private Boolean broadcaster;

	private Boolean collectibleCompany;

	private Boolean conglomerate;

	private Boolean digitalVisualEffectsCompany;

	private Boolean distributor;

	private Boolean gameCompany;

	private Boolean filmEquipmentCompany;

	private Boolean makeUpEffectsStudio;

	private Boolean mattePaintingCompany;

	private Boolean modelAndMiniatureEffectsCompany;

	private Boolean postProductionCompany;

	private Boolean productionCompany;

	private Boolean propCompany;

	private Boolean recordLabel;

	private Boolean specialEffectsCompany;

	private Boolean tvAndFilmProductionCompany;

	private Boolean videoGameCompany;

	private RequestSortDTO sort;

}
