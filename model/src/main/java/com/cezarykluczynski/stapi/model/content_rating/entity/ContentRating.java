package com.cezarykluczynski.stapi.model.content_rating.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.content_rating.entity.enums.ContentRatingSystem;
import com.cezarykluczynski.stapi.model.content_rating.repository.ContentRatingRepository;
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
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_HELPER, repository = ContentRatingRepository.class, apiEntity = false,
		singularName = "content rating", pluralName = "content ratings")
public class ContentRating {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "content_rating_sequence_generator")
	@SequenceGenerator(name = "content_rating_sequence_generator", sequenceName = "content_rating_sequence", allocationSize = 1)
	private Long id;

	@Column(length = 14, name = "u_id")
	private String uid;

	@Enumerated(EnumType.STRING)
	private ContentRatingSystem contentRatingSystem;

	private String rating;

}
