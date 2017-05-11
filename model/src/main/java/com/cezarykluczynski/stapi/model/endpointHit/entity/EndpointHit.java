package com.cezarykluczynski.stapi.model.endpointHit.entity;


import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.endpointHit.entity.enums.EndpointType;
import com.cezarykluczynski.stapi.model.endpointHit.repository.EndpointHitRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TrackedEntity(type = TrackedEntityType.TECHNICAL, repository = EndpointHitRepository.class, apiEntity = false, metricsEntity = true,
		singularName = "endpoint hit", pluralName = "endpoint hits")
@Table(name = "endpoint_hit", schema = "stapi_metrics")
public class EndpointHit {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endpoint_hit_sequence_generator")
	@SequenceGenerator(name = "endpoint_hit_sequence_generator", sequenceName = "endpoint_hit_sequence", schema = "stapi_metrics", allocationSize = 1)
	private Long id;

	private String endpointName;

	private String methodName;

	@Enumerated(EnumType.STRING)
	private EndpointType endpointType;

	private Long numberOfHits;

}
