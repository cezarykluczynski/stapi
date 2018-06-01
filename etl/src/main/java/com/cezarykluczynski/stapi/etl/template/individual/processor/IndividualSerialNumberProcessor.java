package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class IndividualSerialNumberProcessor implements ItemProcessor<String, String> {

	private static final String SERIAL_NUMBER = "serial number";

	private final WikitextApi wikitextApi;

	public IndividualSerialNumberProcessor(WikitextApi wikitextApi) {
		this.wikitextApi = wikitextApi;
	}

	@Override
	public String process(String item) throws Exception {
		if (StringUtils.isBlank(item)) {
			return null;
		}

		List<PageLink> pageLinks = wikitextApi.getPageLinksFromWikitext(item);

		if (pageLinks.size() == 0) {
			return item;
		}

		return pageLinks.stream()
				.filter(pageLink -> SERIAL_NUMBER.equalsIgnoreCase(pageLink.getTitle()))
				.findFirst()
				.map(PageLink::getDescription)
				.orElse(null);
	}

}
