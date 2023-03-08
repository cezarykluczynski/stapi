package com.cezarykluczynski.stapi.model.occupation.entity;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.occupation.repository.OccupationRepository;
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
@TrackedEntity(type = TrackedEntityType.FICTIONAL_PRIMARY, repository = OccupationRepository.class, singularName = "occupation",
		pluralName = "occupations", restApiVersion = "v2")
public class Occupation extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "occupation_sequence_generator")
	@SequenceGenerator(name = "occupation_sequence_generator", sequenceName = "occupation_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String name;

	private Boolean artsOccupation;

	private Boolean communicationOccupation;

	private Boolean economicOccupation;

	private Boolean educationOccupation;

	private Boolean entertainmentOccupation;

	private Boolean illegalOccupation;

	private Boolean legalOccupation;

	private Boolean medicalOccupation;

	private Boolean scientificOccupation;

	private Boolean sportsOccupation;

	private Boolean victualOccupation;

	@ManyToMany(mappedBy = "occupations", targetEntity = Character.class)
	private Set<Character> characters = Sets.newHashSet();

}
