package com.cezarykluczynski.stapi.model.performer.entity;

import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Performer implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="performer_sequence_generator")
	@SequenceGenerator(name="performer_sequence_generator", sequenceName="performer_sequence", allocationSize = 1)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "page_id")
	private Page page;

	@Column(nullable = false)
	private String name;

	private String birthName;

	// TODO: make into enum
	private String gender;

	private LocalDate dateOfBirth;

	private String placeOfBirth;

	private LocalDate dateOfDeath;

	private String placeOfDeath;

	private String roles;

	private Boolean animalPerformer;

	private Boolean disPerformer;

	@Column(name = "ds9_performer")
	private Boolean ds9Performer;

	private Boolean entPerformer;

	private Boolean filmPerformer;

	private Boolean standInPerformer;

	private Boolean stuntPerformer;

	private Boolean tasPerformer;

	private Boolean tngPerformer;

	private Boolean tosPerformer;

	private Boolean videoGamePerformer;

	private Boolean voicePerformer;

	private Boolean voyPerformer;

}
