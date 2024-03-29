package com.cezarykluczynski.stapi.model.platform.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.platform.repository.PlatformRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_HELPER, repository = PlatformRepository.class, apiEntity = false, singularName = "platform",
		pluralName = "platforms")
public class Platform {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "platform_sequence_generator")
	@SequenceGenerator(name = "platform_sequence_generator", sequenceName = "platform_sequence", allocationSize = 1)
	private Long id;

	@Column(length = 14, name = "u_id")
	private String uid;

	private String name;

}
