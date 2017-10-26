package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.etl.template.common.processor.PageLinkToYearProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DayMonthPageLinkProcessor;
import com.cezarykluczynski.stapi.etl.template.individual.dto.DayMonthDTO;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualLifeBoundaryDTO;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO: places
@Service
@Slf4j
public class IndividualLifeBoundaryProcessor implements ItemProcessor<String, IndividualLifeBoundaryDTO> {

	private final WikitextApi wikitextApi;

	private final PageLinkToYearProcessor pageLinkToYearProcessor;

	private final DayMonthPageLinkProcessor dayMonthPageLinkProcessor;

	private final DayInMonthProximityFindingProcessor dayInMonthProximityFindingProcessor;

	public IndividualLifeBoundaryProcessor(WikitextApi wikitextApi, PageLinkToYearProcessor pageLinkToYearProcessor,
			DayMonthPageLinkProcessor dayMonthPageLinkProcessor, DayInMonthProximityFindingProcessor dayInMonthProximityFindingProcessor) {
		this.wikitextApi = wikitextApi;
		this.pageLinkToYearProcessor = pageLinkToYearProcessor;
		this.dayMonthPageLinkProcessor = dayMonthPageLinkProcessor;
		this.dayInMonthProximityFindingProcessor = dayInMonthProximityFindingProcessor;
	}

	@Override
	public IndividualLifeBoundaryDTO process(String item) throws Exception {
		IndividualLifeBoundaryDTO individualLifeBoundaryDTO = new IndividualLifeBoundaryDTO();
		List<PageLink> pageLinks = wikitextApi.getPageLinksFromWikitext(item);

		if (pageLinks.size() > 1) {
			log.info("Parsing page links: {}", pageLinks);
		}

		for (PageLink pageLink : pageLinks) {
			Integer yearFromProcessor = pageLinkToYearProcessor.process(pageLink);
			Integer currentYear = individualLifeBoundaryDTO.getYear();

			if (yearFromProcessor != null) {
				if (currentYear == null || currentYear.equals(yearFromProcessor)) {
					individualLifeBoundaryDTO.setYear(yearFromProcessor);
				} else {
					log.warn("Trying to set life boundary year value to \"{}\", but value \"{}\" already present, original value: \"{}\"",
							yearFromProcessor, currentYear, item);
				}
			} else {
				DayMonthDTO dayMonthDTO = dayMonthPageLinkProcessor.process(pageLink);
				if (individualLifeBoundaryDTO.getDay() == null) {
					individualLifeBoundaryDTO.setDay(dayMonthDTO.getDay());
				}

				if (individualLifeBoundaryDTO.getMonth() == null && dayMonthDTO.getMonth() != null) {
					individualLifeBoundaryDTO.setMonth(dayMonthDTO.getMonth().getValue());
				}

				if (individualLifeBoundaryDTO.getMonth() != null && individualLifeBoundaryDTO.getDay() == null) {
					individualLifeBoundaryDTO.setDay(dayInMonthProximityFindingProcessor.process(Pair.of(item, pageLink)));
				}
			}
		}

		return individualLifeBoundaryDTO;
	}

}
