package com.cezarykluczynski.stapi.model.content_rating.repository;

import com.cezarykluczynski.stapi.model.content_rating.entity.ContentRating;
import com.cezarykluczynski.stapi.model.content_rating.entity.enums.ContentRatingSystem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContentRatingRepository extends JpaRepository<ContentRating, Long> {

	Optional<ContentRating> findByContentRatingSystemAndRating(ContentRatingSystem contentRatingSystem, String rating);

}
