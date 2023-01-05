package com.cezarykluczynski.stapi.model.endpoint_hit.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.endpoint_hit.entity.enums.EndpointType;
import com.cezarykluczynski.stapi.model.endpoint_hit.repository.EndpointHitRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "endpoint_hit", schema = "stapi_users")
@ToString
@TrackedEntity(type = TrackedEntityType.TECHNICAL, repository = EndpointHitRepository.class, apiEntity = false, metricsEntity = true,
		singularName = "endpoint hit", pluralName = "endpoint hits")
public class EndpointHit {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endpoint_hit_sequence_generator")
	@SequenceGenerator(name = "endpoint_hit_sequence_generator", sequenceName = "endpoint_hit_sequence", schema = "stapi_users", allocationSize = 1)
	private Long id;

	private String endpointName;

	private String methodName;

	@Enumerated(EnumType.STRING)
	private EndpointType endpointType;

	private Long numberOfHits;

}
