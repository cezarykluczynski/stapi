package com.cezarykluczynski.stapi.model.throttle.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.throttle.entity.enums.ThrottleType;
import com.cezarykluczynski.stapi.model.throttle.repository.ThrottleRepository;
import lombok.Data;
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
import java.time.LocalDateTime;

@Data
@Entity
@ToString
@TrackedEntity(type = TrackedEntityType.TECHNICAL, repository = ThrottleRepository.class, apiEntity = false, singularName = "throttle",
		pluralName = "throttles")
@Table(name = "throttle", schema = "stapi_users")
public class Throttle {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "page_sequence_generator")
	@SequenceGenerator(name = "page_sequence_generator", sequenceName = "page_sequence", schema = "stapi_users", allocationSize = 1)
	private Long id;

	@Enumerated(EnumType.STRING)
	private ThrottleType throttleType;

	private String ipAddress;

	private String apiKey;

	private Integer hitsLimit;

	private Integer remainingHits;

	private LocalDateTime lastHitTime;

}
