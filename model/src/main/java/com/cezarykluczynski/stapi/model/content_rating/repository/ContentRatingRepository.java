package com.cezarykluczynski.stapi.model.content_rating.repository;

import com.cezarykluczynski.stapi.model.content_rating.entity.ContentRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRatingRepository extends JpaRepository<ContentRating, Long> {
}
