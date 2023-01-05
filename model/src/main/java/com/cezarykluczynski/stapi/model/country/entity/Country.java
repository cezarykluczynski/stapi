package com.cezarykluczynski.stapi.model.country.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.country.repository.CountryRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_HELPER, repository = CountryRepository.class, apiEntity = false, singularName = "country",
		pluralName = "countries")
public class Country {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "content_language_sequence_generator")
	@SequenceGenerator(name = "content_language_sequence_generator", sequenceName = "content_language_sequence", allocationSize = 1)
	private Long id;

	@Column(length = 14, name = "u_id")
	private String uid;

	private String name;

	@SuppressWarnings("MemberName")
	@Column(name = "iso_3166_1_alpha_2_code")
	private String iso31661Alpha2Code;

}
