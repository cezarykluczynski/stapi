package com.cezarykluczynski.stapi.etl.template.common.processor;

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateYearDTO;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.enums.StardateYearSource;
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.StardateYearCandidateDTO;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class StardateYearProcessor implements ItemProcessor<StardateYearCandidateDTO, StardateYearDTO> {

	private final WikitextApi wikitextApi;

	public StardateYearProcessor(WikitextApi wikitextApi) {
		this.wikitextApi = wikitextApi;
	}

	@Override
	public StardateYearDTO process(StardateYearCandidateDTO item) throws Exception {
		String value = item.getValue();
		String imageType = stardateYearSourceToString(item.getSource());
		String title = item.getTitle();

		StardateYearDTO stardateYearDTO = new StardateYearDTO();

		if (value == null) {
			return stardateYearDTO;
		}

		List<String> dateParts = Lists.newArrayList(value.split("\\s"));

		if (dateParts.size() == 1 || dateParts.size() == 2) {
			String stardate = dateParts.get(0);
			enrichWithStardates(stardateYearDTO, stardate, imageType, title);
		}

		if (dateParts.size() == 2) {
			String years = dateParts.get(1);
			enrichWithYears(stardateYearDTO, years, imageType, title);
		}

		return stardateYearDTO;
	}

	private void enrichWithYears(StardateYearDTO stardateYearDTO, String years, String imageType, String title) {
		List<PageLink> pageLinkList = wikitextApi.getPageLinksFromWikitext(years);
		if (!pageLinkList.isEmpty()) {

			stardateYearDTO.setYearFrom(parseYear(pageLinkList.get(0).getTitle(), true, title, imageType));

			if (pageLinkList.size() > 1) {
				stardateYearDTO.setYearTo(parseYear(pageLinkList.get(1).getTitle(), false, title, imageType));
			}

			if (stardateYearDTO.getYearTo() == null) {
				stardateYearDTO.setYearTo(stardateYearDTO.getYearFrom());
			}

			if (stardateYearDTO.getYearFrom() != null && stardateYearDTO.getYearTo() != null
					&& stardateYearDTO.getYearFrom() > stardateYearDTO.getYearTo()) {
				int laterDate = stardateYearDTO.getYearFrom();
				stardateYearDTO.setYearFrom(stardateYearDTO.getYearTo());
				stardateYearDTO.setYearTo(laterDate);
			}

		} else {
			log.warn("Could not get any date links from {} \"{}\" string \"{}\"", imageType, title, years);
		}
	}

	private void enrichWithStardates(StardateYearDTO stardateYearDTO, String stardate, String imageType, String title) {
		if (stardate != null && !stardate.contains("Unknown")) {
			List<String> stardateParts = Lists.newArrayList(stardate.split("-"));
			try {
				if (!stardateParts.isEmpty()) {
					stardateYearDTO.setStardateFrom(Float.valueOf(stardateParts.get(0)));
				}
			} catch (NumberFormatException e) {
				log.info("Could not cast {} \"{}\" \"from\" stardate {} to float", imageType, title, stardateParts.get(0));
			}

			try {
				if (stardateParts.size() > 1) {
					stardateYearDTO.setStardateTo(Float.valueOf(stardateParts.get(1)));
				}
			} catch (NumberFormatException e) {
				log.info("Could not cast {} \"to\" stardate {} to float", imageType, title, stardateParts.get(1));
			}

			if (stardateYearDTO.getStardateTo() == null) {
				stardateYearDTO.setStardateTo(stardateYearDTO.getStardateFrom());
			}

			if (stardateYearDTO.getStardateFrom() != null && stardateYearDTO.getStardateTo() != null
					&& stardateYearDTO.getStardateFrom() > stardateYearDTO.getStardateTo()) {
				Float laterStardate = stardateYearDTO.getStardateFrom();
				stardateYearDTO.setStardateFrom(stardateYearDTO.getStardateTo());
				stardateYearDTO.setStardateTo(laterStardate);
			}
		}
	}

	private Integer parseYear(String linkTitle, boolean from, String templateTitle, String imageType) {
		try {
			Integer year = Integer.valueOf(linkTitle);
			if (year < 1000 || year > 9999) {
				String type = from ? "\"from\"" : "\"to\"";
				log.info("Tried to parse {} {} year \"{}\", but it was out of range", imageType, type, year);
				return null;
			}

			return year;
		} catch (NumberFormatException e) {
			log.warn("Could not cast {} {} link \"{}\" to year", imageType, templateTitle, linkTitle);
			return null;
		}
	}

	private static String stardateYearSourceToString(StardateYearSource stardateYearSource) {
		return stardateYearSource.toString().toLowerCase();
	}

}
