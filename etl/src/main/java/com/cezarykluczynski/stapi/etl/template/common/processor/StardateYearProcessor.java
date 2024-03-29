package com.cezarykluczynski.stapi.etl.template.common.processor;

import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateYearDTO;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.enums.StardateYearSource;
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.StardateYearCandidateDTO;
import com.cezarykluczynski.stapi.util.tool.StringUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

			stardateYearDTO.setYearFrom(parseYear(pageLinkList.get(0).getTitle(), true, imageType));

			if (pageLinkList.size() > 1) {
				stardateYearDTO.setYearTo(parseYear(pageLinkList.get(1).getTitle(), false, imageType));
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

	@SuppressWarnings("NPathComplexity")
	private void enrichWithStardates(StardateYearDTO stardateYearDTO, String stardate, String imageType, String title) {
		if (stardate != null && !stardate.contains("Unknown")) {
			List<String> stardateParts = Lists.newArrayList(stardate.split("-"));
			if (!stardateParts.isEmpty()) {
				final String wikitextWithoutLinks = wikitextApi.getWikitextWithoutLinks(stardateParts.get(0));
				try {
					if (StringUtil.isNotNull(wikitextWithoutLinks) && StringUtils.isNotBlank(wikitextWithoutLinks)) {
						Integer yearCandidate = parseYear(wikitextWithoutLinks, true, imageType);
						if (yearCandidate != null) {
							stardateYearDTO.setYearFrom(yearCandidate);
						} else {
							stardateYearDTO.setStardateFrom(Float.valueOf(wikitextWithoutLinks));
						}
					}
				} catch (NumberFormatException e) {
					log.info("Could not cast {} \"{}\" \"from\" stardate \"{}\" to float", imageType, title, stardateParts.get(0));
				}
			}

			if (stardateParts.size() > 1) {
				final String wikitextWithoutLinks = wikitextApi.getWikitextWithoutLinks(stardateParts.get(1));
				try {
					if (StringUtil.isNotNull(wikitextWithoutLinks) && StringUtils.isNotBlank(wikitextWithoutLinks)) {
						Integer yearCandidate = parseYear(wikitextWithoutLinks, false, imageType);
						if (yearCandidate != null) {
							stardateYearDTO.setYearTo(yearCandidate);
						} else {
							stardateYearDTO.setStardateTo(Float.valueOf(wikitextWithoutLinks));
						}
					}
				} catch (NumberFormatException e) {
					log.info("Could not cast {} \"{}\" \"to\" stardate \"{}\" to float", imageType, title, stardateParts.get(1));
				}
			}
			if (stardateYearDTO.getStardateFrom() != null && stardateYearDTO.getStardateTo() == null) {
				stardateYearDTO.setStardateTo(stardateYearDTO.getStardateFrom());
			}
			if (stardateYearDTO.getYearFrom() != null && stardateYearDTO.getYearTo() == null) {
				stardateYearDTO.setYearTo(stardateYearDTO.getYearFrom());

			}

			if (stardateYearDTO.getStardateFrom() != null && stardateYearDTO.getStardateTo() != null
					&& stardateYearDTO.getStardateFrom() > stardateYearDTO.getStardateTo()) {
				Float laterStardate = stardateYearDTO.getStardateFrom();
				stardateYearDTO.setStardateFrom(stardateYearDTO.getStardateTo());
				stardateYearDTO.setStardateTo(laterStardate);
			}
		}
	}

	private Integer parseYear(String linkTitle, boolean from, String imageType) {
		try {
			Integer year = Integer.valueOf(linkTitle);
			if (year < 1000 || year > 9999) {
				String type = from ? "\"from\"" : "\"to\"";
				log.info("Tried to parse {} {} year \"{}\", but it was out of range", imageType, type, year);
				return null;
			}

			return year;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private static String stardateYearSourceToString(StardateYearSource stardateYearSource) {
		return stardateYearSource.toString().toLowerCase();
	}

}
