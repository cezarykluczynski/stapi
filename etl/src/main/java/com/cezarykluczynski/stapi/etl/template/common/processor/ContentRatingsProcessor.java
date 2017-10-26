package com.cezarykluczynski.stapi.etl.template.common.processor;

import com.cezarykluczynski.stapi.etl.template.common.factory.ContentRatingFactory;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFilter;
import com.cezarykluczynski.stapi.model.content_rating.entity.ContentRating;
import com.cezarykluczynski.stapi.model.content_rating.entity.enums.ContentRatingSystem;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class ContentRatingsProcessor implements ItemProcessor<Template.Part, Set<ContentRating>> {

	private final TemplateFilter templateFilter;

	private final ContentRatingFactory contentRatingFactory;

	public ContentRatingsProcessor(TemplateFilter templateFilter, ContentRatingFactory contentRatingFactory) {
		this.templateFilter = templateFilter;
		this.contentRatingFactory = contentRatingFactory;
	}

	@Override
	public Set<ContentRating> process(Template.Part item) throws Exception {
		Set<ContentRating> contentRatingSet = Sets.newHashSet();

		List<Template> videoRatingsTemplateList = templateFilter.filterByTitle(item.getTemplates(), TemplateTitle.VIDEO_RATINGS, TemplateTitle.VR,
				TemplateTitle.GAME_RATINGS, TemplateTitle.GR);

		if (videoRatingsTemplateList.isEmpty()) {
			return contentRatingSet;
		}

		addAllFoundRatings(videoRatingsTemplateList, contentRatingSet);

		return contentRatingSet;
	}

	private void addAllFoundRatings(List<Template> videoRatingsTemplateList, Set<ContentRating> contentRatingSet) {
		for (Template template : videoRatingsTemplateList) {
			List<Template.Part> templatePartList = template.getParts();
			for (Template.Part part : templatePartList) {
				if (!isEmpty(part)) {
					tryAddPart(part, contentRatingSet);
				}
			}
		}
	}

	private void tryAddPart(Template.Part part, Set<ContentRating> contentRatingSet) {
		ContentRatingSystem contentRatingSystem = tryMap(part.getKey());
		String rating = StringUtils.upperCase(part.getValue());

		if (contentRatingSystem != null && rating != null) {
			createAndAdd(contentRatingSystem, rating, contentRatingSet);
		}
	}

	private boolean isEmpty(Template.Part part) {
		return StringUtils.isEmpty(part.getKey()) && StringUtils.isEmpty(part.getValue()) && CollectionUtils.isEmpty(part.getTemplates());
	}

	private ContentRatingSystem tryMap(String key) {
		try {
			return ContentRatingSystem.valueOf(StringUtils.upperCase(key));
		} catch (Exception e) {
			log.info("Could not map string \"{}\" to ContentRatingSystem", key);
			return null;
		}
	}

	private void createAndAdd(ContentRatingSystem contentRatingSystem, String rating, Set<ContentRating> contentRatingSet) {
		ContentRating contentRating = contentRatingFactory.create(contentRatingSystem, rating);

		if (contentRating != null) {
			contentRatingSet.add(contentRating);
		}
	}

}
