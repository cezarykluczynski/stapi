package com.cezarykluczynski.stapi.etl.template.common.factory;

import com.cezarykluczynski.stapi.model.content_rating.entity.ContentRating;
import com.cezarykluczynski.stapi.model.content_rating.entity.enums.ContentRatingSystem;
import com.cezarykluczynski.stapi.model.content_rating.repository.ContentRatingRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContentRatingFactory {

	private final ContentRatingRepository contentRatingRepository;

	public ContentRatingFactory(ContentRatingRepository contentRatingRepository) {
		this.contentRatingRepository = contentRatingRepository;
	}

	public synchronized ContentRating create(ContentRatingSystem contentRatingSystem, String rating) {
		Optional<ContentRating> contentRatingOptional = contentRatingRepository.findByContentRatingSystemAndRating(contentRatingSystem, rating);

		if (contentRatingOptional.isPresent()) {
			return contentRatingOptional.get();
		} else {
			ContentRating contentRating = new ContentRating();
			contentRating.setContentRatingSystem(contentRatingSystem);
			contentRating.setRating(rating);
			contentRatingRepository.save(contentRating);
			return contentRating;
		}
	}

}
