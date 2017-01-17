package com.cezarykluczynski.stapi.model.character.entity;

import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.common.entity.enums.BloodType;
import com.cezarykluczynski.stapi.model.common.entity.enums.Gender;
import com.cezarykluczynski.stapi.model.common.entity.enums.MaritalStatus;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, exclude = {"performers", "episodes", "movies"})
@EqualsAndHashCode(callSuper = true, exclude = {"performers", "episodes", "movies"})
public class Character extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "character_sequence_generator")
	@SequenceGenerator(name = "character_sequence_generator", sequenceName = "character_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	private Integer yearOfBirth;

	private Integer monthOfBirth;

	private Integer dayOfBirth;

	private String placeOfBirth;

	private Integer yearOfDeath;

	private Integer monthOfDeath;

	private Integer dayOfDeath;

	private String placeOfDeath;

	private Integer height;

	private Integer weight;

	private Boolean deceased;

	@Enumerated(EnumType.STRING)
	private BloodType bloodType;

	@Enumerated(EnumType.STRING)
	private MaritalStatus maritalStatus;

	private String serialNumber;

	private Boolean mirror;

	private Boolean alternateReality;

	@ManyToMany(mappedBy = "characters", targetEntity = Performer.class)
	private Set<Performer> performers = Sets.newHashSet();

	@ManyToMany(mappedBy = "characters", targetEntity = Episode.class)
	private Set<Episode> episodes = Sets.newHashSet();

	@ManyToMany(mappedBy = "characters", targetEntity = Movie.class)
	private Set<Movie> movies = Sets.newHashSet();

}
