package com.cezarykluczynski.stapi.model.reference.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.reference.entity.enums.ReferenceType;
import com.cezarykluczynski.stapi.model.reference.repository.ReferenceRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Data
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_PRIMARY, repository = ReferenceRepository.class, apiEntity = false, singularName = "reference",
		pluralName = "characters")
public class Reference {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reference_sequence_generator")
	@SequenceGenerator(name = "reference_sequence_generator", sequenceName = "reference_sequence", allocationSize = 1)
	private Long id;

	@Column(name = "u_id")
	private String uid;

	@Enumerated(EnumType.STRING)
	private ReferenceType referenceType;

	private String referenceNumber;

}
