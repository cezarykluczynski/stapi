package com.cezarykluczynski.stapi.model.performer.entity;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.common.entity.RealWorldPerson;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.google.common.collect.Sets;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, exclude = {"performances", "stuntPerformances", "standInPerformances", "characters"})
@EqualsAndHashCode(callSuper = true, exclude = {"performances", "stuntPerformances", "standInPerformances",
		"characters"})
public class Performer extends RealWorldPerson implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "performer_sequence_generator")
	@SequenceGenerator(name = "performer_sequence_generator", sequenceName = "performer_sequence", allocationSize = 1)
	private Long id;

	private boolean animalPerformer;

	private boolean disPerformer;

	@Column(name = "ds9_performer")
	private boolean ds9Performer;

	private boolean entPerformer;

	private boolean filmPerformer;

	private boolean standInPerformer;

	private boolean stuntPerformer;

	private boolean tasPerformer;

	private boolean tngPerformer;

	private boolean tosPerformer;

	private boolean videoGamePerformer;

	private boolean voicePerformer;

	private boolean voyPerformer;

	@ManyToMany(mappedBy = "performers")
	private Set<Episode> performances = Sets.newHashSet();

	@ManyToMany(mappedBy = "stuntPerformers")
	private Set<Episode> stuntPerformances = Sets.newHashSet();

	@ManyToMany(mappedBy = "standInPerformers")
	private Set<Episode> standInPerformances = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "performers_characters",
			joinColumns = @JoinColumn(name = "performer_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "character_id", nullable = false, updatable = false))
	private Set<Character> characters = Sets.newHashSet();

}
