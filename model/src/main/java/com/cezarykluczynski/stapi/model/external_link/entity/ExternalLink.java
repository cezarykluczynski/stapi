package com.cezarykluczynski.stapi.model.external_link.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.external_link.entity.enums.ExternalLinkType;
import com.cezarykluczynski.stapi.model.external_link.entity.enums.ExternalLinkTypeVariant;
import com.cezarykluczynski.stapi.model.external_link.repository.ExternalLinkRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_HELPER, repository = ExternalLinkRepository.class, apiEntity = false,
		singularName = "external link", pluralName = "external links")
public class ExternalLink {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "external_link_sequence_generator")
	@SequenceGenerator(name = "external_link_sequence_generator", sequenceName = "external_link_sequence", allocationSize = 1)
	private Long id;

	@Column(length = 14, name = "u_id")
	private String uid;

	@Enumerated(EnumType.STRING)
	private ExternalLinkType type;

	@Enumerated(EnumType.STRING)
	private ExternalLinkTypeVariant variant;

	private String link;

	private String resourceId;


}
