package com.cezarykluczynski.stapi.model.character.entity;

import com.cezarykluczynski.stapi.model.character.repository.CharacterSpeciesRepository;
import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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

import java.io.Serializable;

@Data
@Entity

@ToString(exclude = {"species"})
@EqualsAndHashCode(exclude = {"species"})
@TrackedEntity(type = TrackedEntityType.FICTIONAL_HELPER, repository = CharacterSpeciesRepository.class, apiEntity = false,
		singularName = "character species", pluralName = "characters species")
@SuppressFBWarnings("SE_BAD_FIELD")
public class CharacterSpecies implements Serializable {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "character_species_sequence_generator")
	@SequenceGenerator(name = "character_species_sequence_generator", sequenceName = "character_species_sequence", allocationSize = 1)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "species_id")
	private Species species;

	private Long numerator;

	private Long denominator;

}
