package com.cezarykluczynski.stapi.model.consent.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.consent.entity.enums.ConsentType;
import com.cezarykluczynski.stapi.model.consent.repository.ConsentRepository;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "consent", schema = "stapi_users")
@TrackedEntity(type = TrackedEntityType.TECHNICAL, repository = ConsentRepository.class, apiEntity = false, singularName = "consent",
		pluralName = "consents")
public class Consent {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "consent_sequence_generator")
	@SequenceGenerator(name = "consent_sequence_generator", sequenceName = "consent_sequence", schema = "stapi_users", allocationSize = 1)
	private Long id;

	@Enumerated(EnumType.STRING)
	private ConsentType consentType;

}
