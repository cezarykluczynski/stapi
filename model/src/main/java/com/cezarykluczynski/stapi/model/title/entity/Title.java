package com.cezarykluczynski.stapi.model.title.entity;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.title.repository.TitleRepository;
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
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Set;

@Data
@Entity
@ToString(callSuper = true, exclude = {"characters"})
@EqualsAndHashCode(callSuper = true, exclude = {"characters"})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@TrackedEntity(type = TrackedEntityType.FICTIONAL_PRIMARY, repository = TitleRepository.class, singularName = "title", pluralName = "titles",
		restApiVersion = "v2")
public class Title extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "title_sequence_generator")
	@SequenceGenerator(name = "title_sequence_generator", sequenceName = "title_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String name;

	private Boolean militaryRank;

	private Boolean fleetRank;

	private Boolean religiousTitle;

	private Boolean educationTitle;

	private Boolean position; // TODO: remove when API V1 is removed

	private Boolean mirror;

	@ManyToMany(mappedBy = "titles", targetEntity = Character.class)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Character> characters = Sets.newHashSet();

}
