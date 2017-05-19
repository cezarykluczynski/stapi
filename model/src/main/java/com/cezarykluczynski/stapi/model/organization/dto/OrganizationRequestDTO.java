package com.cezarykluczynski.stapi.model.organization.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class OrganizationRequestDTO {

	private String uid;

	private String name;

	private Boolean government;

	private Boolean intergovernmentalOrganization;

	private Boolean researchOrganization;

	private Boolean sportOrganization;

	private Boolean medicalOrganization;

	private Boolean militaryOrganization;

	private Boolean militaryUnit;

	private Boolean governmentAgency;

	private Boolean lawEnforcementAgency;

	private Boolean prisonOrPenalColony;

	private Boolean mirror;

	private Boolean alternateReality;

	private RequestSortDTO sort;

}
