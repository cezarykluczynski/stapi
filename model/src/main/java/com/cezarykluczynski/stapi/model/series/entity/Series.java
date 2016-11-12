package com.cezarykluczynski.stapi.model.series.entity;

import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class Series extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="series_sequence_generator")
	@SequenceGenerator(name="series_sequence_generator", sequenceName="series_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String title;

	private String abbreviation;

//	TODO
//	private Company productionCompany;

//	TODO
//	private Company originalBroadcaster;

	private Integer productionStartYear;

	private Integer productionEndYear;

	private LocalDate originalRunStartDate;

	private LocalDate originalRunEndDate;

}
