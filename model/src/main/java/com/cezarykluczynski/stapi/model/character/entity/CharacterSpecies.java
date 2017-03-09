package com.cezarykluczynski.stapi.model.character.entity;

import com.cezarykluczynski.stapi.model.species.entity.Species;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"species"})
@EqualsAndHashCode(exclude = {"species"})
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
