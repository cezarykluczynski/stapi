package com.cezarykluczynski.stapi.model.character.entity;

import com.cezarykluczynski.stapi.model.character.repository.CharacterRelationRepository;
import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Data
@Entity

@ToString(exclude = {"source", "target"})
@EqualsAndHashCode(exclude = {"source", "target"})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@TrackedEntity(type = TrackedEntityType.FICTIONAL_HELPER, repository = CharacterRelationRepository.class, apiEntity = false,
		singularName = "character relation", pluralName = "character relations")
public class CharacterRelation {

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "source_character_id")
	private Character source;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "target_character_id")
	private Character target;

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "character_relations_sequence_generator")
	@SequenceGenerator(name = "character_relations_sequence_generator", sequenceName = "character_relations_sequence", allocationSize = 1)
	private Long id;

	private String type;

}
