package com.cezarykluczynski.stapi.model.genre.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.genre.repository.GenreRepository;
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
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_HELPER, repository = GenreRepository.class, apiEntity = false,
		singularName = "genre", pluralName = "genres")
public class Genre {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre_sequence_generator")
	@SequenceGenerator(name = "genre_sequence_generator", sequenceName = "genre_sequence", allocationSize = 1)
	private Long id;

	@Column(length = 14, name = "u_id")
	private String uid;

	private String name;

}
