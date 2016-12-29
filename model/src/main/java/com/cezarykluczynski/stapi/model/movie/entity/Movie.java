package com.cezarykluczynski.stapi.model.movie.entity;

import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.google.common.collect.Sets;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"screenplayAuthors", "storyAuthors", "directors", "producers", "staff", "performers",
		"stuntPerformers", "standInPerformers", "characters"})
@EqualsAndHashCode(callSuper = true, exclude = {"screenplayAuthors", "storyAuthors", "directors", "producers", "staff",
		"performers", "stuntPerformers", "standInPerformers", "characters"})
public class Movie extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_sequence_generator")
	@SequenceGenerator(name = "movie_sequence_generator", sequenceName ="movie_sequence", allocationSize = 1)
	private Long id;

	private String title;

	private String titleBulgarian;

	private String titleCatalan;

	private String titleChineseTraditional;

	private String titleGerman;

	private String titleItalian;

	private String titleJapanese;

	private String titlePolish;

	private String titleRussian;

	private String titleSerbian;

	private String titleSpanish;

	private Float stardateFrom;

	private Float stardateTo;

	private Integer yearFrom;

	private Integer yearTo;

	private LocalDate usReleaseDate;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "movies_screenplay_authors",
			joinColumns = @JoinColumn(name = "movie_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "staff_id", nullable = false, updatable = false))
	private Set<Staff> screenplayAuthors = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "movies_story_authors",
			joinColumns = @JoinColumn(name = "movie_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "staff_id", nullable = false, updatable = false))
	private Set<Staff> storyAuthors = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "movies_directors",
			joinColumns = @JoinColumn(name = "movie_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "staff_id", nullable = false, updatable = false))
	private Set<Staff> directors = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "movies_producers",
			joinColumns = @JoinColumn(name = "movie_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "staff_id", nullable = false, updatable = false))
	private Set<Staff> producers = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "movies_staff",
			joinColumns = @JoinColumn(name = "movie_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "staff_id", nullable = false, updatable = false))
	private Set<Staff> staff = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "movies_performers",
			joinColumns = @JoinColumn(name = "movie_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "performer_id", nullable = false, updatable = false))
	private Set<Performer> performers = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "movies_stunt_performers",
			joinColumns = @JoinColumn(name = "movie_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "performer_id", nullable = false, updatable = false))
	private Set<Performer> stuntPerformers = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "movies_stand_in_performers",
			joinColumns = @JoinColumn(name = "movie_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "performer_id", nullable = false, updatable = false))
	private Set<Performer> standInPerformers = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "movies_characters",
			joinColumns = @JoinColumn(name = "movie_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "character_id", nullable = false, updatable = false))
	private Set<Character> characters = Sets.newHashSet();

}
