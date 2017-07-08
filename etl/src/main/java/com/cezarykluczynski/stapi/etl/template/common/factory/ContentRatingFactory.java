package com.cezarykluczynski.stapi.etl.template.common.factory;

import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.content_rating.entity.ContentRating;
import com.cezarykluczynski.stapi.model.content_rating.entity.enums.ContentRatingSystem;
import com.cezarykluczynski.stapi.model.content_rating.repository.ContentRatingRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContentRatingFactory {

	private final ContentRatingRepository contentRatingRepository;

	private final UidGenerator uidGenerator;

	public ContentRatingFactory(ContentRatingRepository contentRatingRepository, UidGenerator uidGenerator) {
		this.contentRatingRepository = contentRatingRepository;
		this.uidGenerator = uidGenerator;
	}

	public synchronized ContentRating create(ContentRatingSystem contentRatingSystem, String rating) {
		String clearRating = StringUtils.replace(rating, "-", "");
		Optional<ContentRating> contentRatingOptional = contentRatingRepository.findByContentRatingSystemAndRating(contentRatingSystem, clearRating);

		if (contentRatingOptional.isPresent()) {
			return contentRatingOptional.get();
		} else {
			ContentRating contentRating = new ContentRating();
			contentRating.setContentRatingSystem(contentRatingSystem);
			contentRating.setRating(clearRating);
			contentRating.setUid(uidGenerator.generateForContentRating(contentRatingSystem, clearRating));
			contentRatingRepository.save(contentRating);
			return contentRating;
		}
	}

}
