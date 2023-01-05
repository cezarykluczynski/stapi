package com.cezarykluczynski.stapi.model.content_language.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.content_language.repository.ContentLanguageRepository;
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
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_HELPER, repository = ContentLanguageRepository.class, apiEntity = false,
		singularName = "content language", pluralName = "content languages")
public class ContentLanguage {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "content_language_sequence_generator")
	@SequenceGenerator(name = "content_language_sequence_generator", sequenceName = "content_language_sequence", allocationSize = 1)
	private Long id;

	@Column(length = 14, name = "u_id")
	private String uid;

	private String name;

	@Column(name = "iso_639_1_code")
	private String iso6391Code;

}
