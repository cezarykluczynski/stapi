package com.cezarykluczynski.stapi.etl.template.common.processor;

import com.cezarykluczynski.stapi.etl.template.util.PatternDictionary;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;

@Service
public class PageLinkToYearProcessor implements ItemProcessor<PageLink, Integer> {

	@Override
	public Integer process(PageLink pageLink) throws Exception {
		if (pageLink == null) {
			return null;
		}

		String title = pageLink.getTitle();
		String description = pageLink.getDescription();

		if (title != null) {
			Matcher titleMatcher = PatternDictionary.YEAR.matcher(title);
			if (titleMatcher.matches()) {
				return Integer.valueOf(title);
			}
		}

		if (description != null) {
			Matcher descriptionMatcher = PatternDictionary.YEAR.matcher(description);
			if (descriptionMatcher.matches()) {
				return Integer.valueOf(description);
			}
		}

		return null;
	}

}
