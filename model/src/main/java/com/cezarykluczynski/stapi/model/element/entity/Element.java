package com.cezarykluczynski.stapi.model.element.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity;
import com.cezarykluczynski.stapi.model.element.repository.ElementRepository;
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
@TrackedEntity(type = TrackedEntityType.FICTIONAL_PRIMARY, repository = ElementRepository.class, singularName = "element", pluralName = "elements",
		restApiVersion = "v2")
public class Element extends PageAwareEntity implements PageAware {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "element_sequence_generator")
	@SequenceGenerator(name = "element_sequence_generator", sequenceName = "element_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String name;

	private String symbol;

	private Integer atomicNumber;

	private Integer atomicWeight;

	private Boolean transuranic;

	private Boolean gammaSeries;

	private Boolean hypersonicSeries;

	private Boolean megaSeries;

	private Boolean omegaSeries;

	private Boolean transonicSeries;

	private Boolean worldSeries;

}
