package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.etl.template.common.processor.PageLinkToYearProcessor;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualLifeBoundaryDTO;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

// TODO: month, days, places
@Service
@Slf4j
public class IndividualLifeBoundaryProcessor implements ItemProcessor<String, IndividualLifeBoundaryDTO> {

	private WikitextApi wikitextApi;

	private PageLinkToYearProcessor pageLinkToYearProcessor;

	@Inject
	public IndividualLifeBoundaryProcessor(WikitextApi wikitextApi, PageLinkToYearProcessor pageLinkToYearProcessor) {
		this.wikitextApi = wikitextApi;
		this.pageLinkToYearProcessor = pageLinkToYearProcessor;
	}

	@Override
	public IndividualLifeBoundaryDTO process(String item) throws Exception {
		IndividualLifeBoundaryDTO individualLifeBoundaryDTO = new IndividualLifeBoundaryDTO();
		List<PageLink> pageLinks = wikitextApi.getPageLinksFromWikitext(item);

		for (PageLink pageLink : pageLinks) {
			Integer yearFromProcessor = pageLinkToYearProcessor.process(pageLink);
			Integer currentYear = individualLifeBoundaryDTO.getYear();

			if (yearFromProcessor != null) {
				if (currentYear == null || currentYear.equals(yearFromProcessor)) {
					individualLifeBoundaryDTO.setYear(yearFromProcessor);
				} else {
					log.error("Trying to set life boundary year value to {}, but value {} already present, " +
							"original value: {}", yearFromProcessor, currentYear, item);
				}
			}
		}

		return individualLifeBoundaryDTO;
	}

}
