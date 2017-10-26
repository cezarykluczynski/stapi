package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.etl.template.common.processor.PageLinkToYearProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class IndividualDateStatusValueToYearProcessor implements ItemProcessor<String, Integer> {

	private static final String CENTURY = "century";

	private final WikitextApi wikitextApi;

	private final PageLinkToYearProcessor pageLinkToYearProcessor;

	public IndividualDateStatusValueToYearProcessor(WikitextApi wikitextApi, PageLinkToYearProcessor pageLinkToYearProcessor) {
		this.wikitextApi = wikitextApi;
		this.pageLinkToYearProcessor = pageLinkToYearProcessor;
	}

	@Override
	public Integer process(String value) throws Exception {
		if (StringUtils.isBlank(value) || value.contains(CENTURY) || value.length() > 4 && "s".equals(String.valueOf(value.charAt(4)))) {
			return null;
		}

		List<PageLink> pageLinkList = wikitextApi.getPageLinksFromWikitext(value);

		if (!pageLinkList.isEmpty()) {
			if (pageLinkList.size() > 1) {
				log.info("More than one link found in date status, links are: {}", pageLinkList);
			}

			List<Integer> years = pageLinkList.stream()
					.map(pageLink -> {
						try {
							return pageLinkToYearProcessor.process(pageLink);
						} catch (Exception e) {
							return null;
						}
					})
					.distinct()
					.collect(Collectors.toList());

			if (years.size() == 1) {
				return years.get(0);
			} else if (years.size() > 1) {
				log.warn("More than one year found in links for status date \"{}\", values are: {}", value, years);
			}
		}

		try {
			return Integer.valueOf(value);
		} catch (NumberFormatException e) {
			log.error("Expected value that can be casted to Integer, but \"{}\" found", value);
			return null;
		}
	}

}
