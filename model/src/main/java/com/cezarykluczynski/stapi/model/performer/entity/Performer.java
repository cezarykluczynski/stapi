package com.cezarykluczynski.stapi.model.performer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Performer {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="performer_sequence_generator")
	@SequenceGenerator(name="performer_sequence_generator", sequenceName="performer_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String name;

	private String birth_name;

	private String gender;

	private LocalDate date_of_birth;

	private String place_of_birth;

	private LocalDate date_of_death;

	private String place_of_death;

	private String roles;

	private Boolean animal_performer;

	private Boolean dis_performer;

	private Boolean ds9_performer;

	private Boolean ent_performer;

	private Boolean film_performer;

	private Boolean stand_in_performer;

	private Boolean stunt_performer;

	private Boolean tas_performer;

	private Boolean tng_performer;

	private Boolean tos_performer;

	private Boolean video_game_performer;

	private Boolean voice_performer;

	private Boolean voy_performer;

}
