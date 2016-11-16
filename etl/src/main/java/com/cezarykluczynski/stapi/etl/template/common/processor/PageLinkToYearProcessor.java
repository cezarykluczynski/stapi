package com.cezarykluczynski.stapi.etl.template.common.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;

@Service
public class PageLinkToYearProcessor implements ItemProcessor<PageLink, Integer> {

	private static final java.util.regex.Pattern YEAR = java.util.regex.Pattern.compile("^\\d{4}$");

	@Override
	public Integer process(PageLink pageLink) throws Exception {
		if (pageLink == null) {
			return null;
		}

		String title = pageLink.getTitle();
		String description = pageLink.getDescription();

		if (title != null) {
			Matcher titleMatcher = YEAR.matcher(title);
			if (titleMatcher.matches()) {
				return Integer.valueOf(title);
			}
		}

		if (description != null) {
			Matcher descriptionMatcher = YEAR.matcher(description);
			if (descriptionMatcher.matches()) {
				return Integer.valueOf(description);
			}
		}

		return null;
	}

}
