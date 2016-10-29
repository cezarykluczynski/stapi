package com.cezarykluczynski.stapi.model.performer.entity;

import com.cezarykluczynski.stapi.model.common.entity.Gender;
import com.cezarykluczynski.stapi.model.page.entity.Page;
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

	@Enumerated(EnumType.STRING)
	private Gender gender;

	private LocalDate dateOfBirth;

	private String placeOfBirth;

	private LocalDate dateOfDeath;

	private String placeOfDeath;

	private String roles;

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

}
