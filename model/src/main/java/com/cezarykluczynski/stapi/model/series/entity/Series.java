package com.cezarykluczynski.stapi.model.series.entity;

import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true, exclude = {"episodes"})
public class Series extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "series_sequence_generator")
	@SequenceGenerator(name = "series_sequence_generator", sequenceName = "series_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String title;

	private String abbreviation;

	private Integer productionStartYear;

	private Integer productionEndYear;

	private LocalDate originalRunStartDate;

	private LocalDate originalRunEndDate;

	private Integer seasonsCount;

	private Integer episodesCount;

	private Integer featureLengthEpisodesCount;

	@OneToMany(mappedBy = "series")
	private Set<Episode> episodes;

//	TODO
//	private Company productionCompany;

//	TODO
//	private Company originalBroadcaster;
}
