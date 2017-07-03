package com.cezarykluczynski.stapi.etl.template.common.processor;

import com.cezarykluczynski.stapi.model.content_rating.entity.ContentRating;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.google.common.collect.Sets;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ContentRatingProcessor implements ItemProcessor<Template.Part, Set<ContentRating>> {

	@Override
	public Set<ContentRating> process(Template.Part item) throws Exception {
		// TODO
		return Sets.newHashSet();
	}

}
