package com.cezarykluczynski.stapi.model.literature.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.literature.repository.LiteratureRepository;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TrackedEntity(type = TrackedEntityType.FICTIONAL_PRIMARY, repository = LiteratureRepository.class, singularName = "literature",
		pluralName = "literature pieces")
public class Literature extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "literature_sequence_generator")
	@SequenceGenerator(name = "literature_sequence_generator", sequenceName = "literature_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String title;

	private Boolean earthlyOrigin;

	private Boolean shakespeareanWork;

	private Boolean report;

	private Boolean scientificLiterature;

	private Boolean technicalManual;

	private Boolean religiousLiterature;


}
