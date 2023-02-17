package com.cezarykluczynski.stapi.model.character.entity;

import com.cezarykluczynski.stapi.model.character.repository.CharacterRelationRepository;
import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Data
@Entity
@ToString(exclude = {"source", "target"})
@EqualsAndHashCode(exclude = {"source", "target"})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@TrackedEntity(type = TrackedEntityType.FICTIONAL_HELPER, repository = CharacterRelationRepository.class, apiEntity = false,
		singularName = "character relation", pluralName = "character relations")
public class CharacterRelation {

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "source_character_id")
	private Character source;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "target_character_id")
	private Character target;

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "character_relations_sequence_generator")
	@SequenceGenerator(name = "character_relations_sequence_generator", sequenceName = "character_relations_sequence", allocationSize = 1)
	private Long id;

	private String type;

}
