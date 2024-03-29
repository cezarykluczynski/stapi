package com.cezarykluczynski.stapi.model.staff.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.RealWorldPerson;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.staff.repository.StaffRepository;
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
@ToString(callSuper = true, exclude = {"writtenEpisodes", "teleplayAuthoredEpisodes", "storyAuthoredEpisodes", "directedEpisodes", "episodes",
		"writtenMovies", "screenplayAuthoredMovies", "storyAuthoredMovies", "directedMovies", "producedMovies", "movies"})
@EqualsAndHashCode(callSuper = true, exclude = {"writtenEpisodes", "teleplayAuthoredEpisodes", "storyAuthoredEpisodes", "directedEpisodes",
		"episodes", "writtenMovies", "screenplayAuthoredMovies", "storyAuthoredMovies", "directedMovies", "producedMovies", "movies"})
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_PRIMARY, repository = StaffRepository.class, singularName = "staff", pluralName = "staff members",
		restApiVersion = "v2")
public class Staff extends RealWorldPerson implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "staff_sequence_generator")
	@SequenceGenerator(name = "staff_sequence_generator", sequenceName = "staff_sequence", allocationSize = 1)
	private Long id;

	private Boolean artDepartment;

	private Boolean artDirector;

	private Boolean productionDesigner;

	@Column(name = "camera_and_electrical")
	private Boolean cameraAndElectricalDepartment;

	private Boolean cinematographer;

	private Boolean castingDepartment;

	private Boolean costumeDepartment;

	private Boolean costumeDesigner;

	private Boolean director;

	@Column(name = "assistant_or_second_unit")
	private Boolean assistantOrSecondUnitDirector;

	private Boolean exhibitAndAttractionStaff;

	private Boolean filmEditor;

	private Boolean filmationProductionStaff;

	private Boolean linguist;

	private Boolean locationStaff;

	private Boolean makeupStaff;

	private Boolean merchandiseStaff;

	private Boolean musicDepartment;

	private Boolean composer;

	private Boolean personalAssistant;

	private Boolean producer;

	private Boolean productionAssociate;

	private Boolean productionStaff;

	private Boolean publicationStaff;

	private Boolean scienceConsultant;

	private Boolean soundDepartment;

	@Column(name = "special_and_visual_effects")
	private Boolean specialAndVisualEffectsStaff;

	private Boolean author;

	private Boolean audioAuthor;

	private Boolean calendarArtist;

	private Boolean comicArtist;

	private Boolean comicAuthor;

	private Boolean comicColorArtist;

	private Boolean comicCoverArtist;

	private Boolean comicInteriorArtist;

	private Boolean comicInkArtist;

	private Boolean comicPencilArtist;

	private Boolean comicLetterArtist;

	private Boolean comicStripArtist;

	private Boolean gameArtist;

	private Boolean gameAuthor;

	private Boolean novelArtist;

	private Boolean novelAuthor;

	private Boolean referenceArtist;

	private Boolean referenceAuthor;

	private Boolean publicationArtist;

	private Boolean publicationDesigner;

	private Boolean publicationEditor;

	private Boolean publicityArtist;

	private Boolean cbsDigitalStaff;

	private Boolean ilmProductionStaff;

	private Boolean specialFeaturesStaff;

	private Boolean storyEditor;

	private Boolean studioExecutive;

	private Boolean stuntDepartment;

	private Boolean transportationDepartment;

	private Boolean videoGameProductionStaff;

	private Boolean writer;

	@ManyToMany(mappedBy = "writers", targetEntity = Episode.class)
	private Set<Episode> writtenEpisodes;

	@ManyToMany(mappedBy = "teleplayAuthors", targetEntity = Episode.class)
	private Set<Episode> teleplayAuthoredEpisodes;

	@ManyToMany(mappedBy = "storyAuthors", targetEntity = Episode.class)
	private Set<Episode> storyAuthoredEpisodes;

	@ManyToMany(mappedBy = "directors", targetEntity = Episode.class)
	private Set<Episode> directedEpisodes;

	@ManyToMany(mappedBy = "staff", targetEntity = Episode.class)
	private Set<Episode> episodes;

	@ManyToMany(mappedBy = "writers", targetEntity = Movie.class)
	private Set<Movie> writtenMovies;

	@ManyToMany(mappedBy = "screenplayAuthors", targetEntity = Movie.class)
	private Set<Movie> screenplayAuthoredMovies;

	@ManyToMany(mappedBy = "storyAuthors", targetEntity = Movie.class)
	private Set<Movie> storyAuthoredMovies;

	@ManyToMany(mappedBy = "directors", targetEntity = Movie.class)
	private Set<Movie> directedMovies;

	@ManyToMany(mappedBy = "producers", targetEntity = Movie.class)
	private Set<Movie> producedMovies;

	@ManyToMany(mappedBy = "producers", targetEntity = Movie.class)
	private Set<Movie> movies;

}
