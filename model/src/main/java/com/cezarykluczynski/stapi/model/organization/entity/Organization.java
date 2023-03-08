package com.cezarykluczynski.stapi.model.organization.entity;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.organization.repository.OrganizationRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.google.common.collect.Sets;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Data
@Entity
@ToString(callSuper = true, exclude = {"characters"})
@EqualsAndHashCode(callSuper = true, exclude = {"characters"})
@TrackedEntity(type = TrackedEntityType.FICTIONAL_PRIMARY, repository = OrganizationRepository.class, singularName = "organization",
		pluralName = "organizations")
public class Organization extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organization_sequence_generator")
	@SequenceGenerator(name = "organization_sequence_generator", sequenceName = "organization_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
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

	@ManyToMany(mappedBy = "organizations", targetEntity = Character.class)
	private Set<Character> characters = Sets.newHashSet();

}
