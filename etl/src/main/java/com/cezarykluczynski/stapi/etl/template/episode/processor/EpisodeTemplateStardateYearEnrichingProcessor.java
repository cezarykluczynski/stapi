package com.cezarykluczynski.stapi.etl.template.episode.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate;
import com.cezarykluczynski.stapi.etl.template.episode.dto.StardateYearDTO;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
@Slf4j
public class EpisodeTemplateStardateYearEnrichingProcessor
		implements ItemEnrichingProcessor<EnrichablePair<Template, EpisodeTemplate>> {

	private static final String S_TITLE = "stitle";
	private static final String WS_DATE = "wsdate";

	private WikitextApi wikitextApi;

	private EpisodeTemplateStardateYearFixedValueProvider episodeTemplateStardateYearFixedValueProvider;

	@Inject
	public EpisodeTemplateStardateYearEnrichingProcessor(WikitextApi wikitextApi,
			EpisodeTemplateStardateYearFixedValueProvider episodeTemplateStardateYearFixedValueProvider) {
		this.wikitextApi = wikitextApi;
		this.episodeTemplateStardateYearFixedValueProvider = episodeTemplateStardateYearFixedValueProvider;
	}

	@Override
	public void enrich(EnrichablePair<Template, EpisodeTemplate> enrichablePair) {
		Template template = enrichablePair.getInput();
		EpisodeTemplate episodeTemplate = enrichablePair.getOutput();

		String title = null;
		boolean stardateFixedValueFound = false;

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case S_TITLE:
					title = value;
					FixedValueHolder<StardateYearDTO> fixedValueHolder
							= episodeTemplateStardateYearFixedValueProvider.getSearchedValue(value);
					stardateFixedValueFound = fixedValueHolder.isFound();
					if (stardateFixedValueFound) {
						StardateYearDTO stardateYearDTO = fixedValueHolder.getValue();

						episodeTemplate.setStardateFrom(stardateYearDTO.getStardateFrom());
						episodeTemplate.setStardateTo(stardateYearDTO.getStardateTo());
						episodeTemplate.setYearFrom(stardateYearDTO.getYearFrom());
						episodeTemplate.setYearTo(stardateYearDTO.getYearTo());
					}
					break;
				case WS_DATE:
					if (!stardateFixedValueFound) {
						setDates(episodeTemplate, value, title);
					}
					break;
			}
		}
	}

	private void setDates(EpisodeTemplate episodeTemplate, String value, String title) {
		if (value == null) {
			return;
		}

		List<String> dateParts = Lists.newArrayList(value.split("\\s"));

		if (dateParts.size() == 1 || dateParts.size() == 2) {
			String stardate = dateParts.get(0);

			if (stardate != null && !stardate.contains("Unknown")) {
				List<String> stardateParts = Lists.newArrayList(stardate.split("-"));
				try {
					if (!stardateParts.isEmpty()) {
						episodeTemplate.setStardateFrom(Float.valueOf(stardateParts.get(0)));
					}
				} catch (NumberFormatException e) {
					log.warn("Could not cast episode {} \"from\" stardate {} to float", title, stardateParts.get(0));
				}

				try {
					if (stardateParts.size() > 1) {
						episodeTemplate.setStardateTo(Float.valueOf(stardateParts.get(1)));
					}
				} catch (NumberFormatException e) {
					log.warn("Could not cast episode {} \"to\" stardate {} to float", title, stardateParts.get(1));
				}

				if (episodeTemplate.getStardateTo() == null) {
					episodeTemplate.setStardateTo(episodeTemplate.getStardateFrom());
				}

				if (episodeTemplate.getStardateFrom() != null && episodeTemplate.getStardateTo() != null) {
					if (episodeTemplate.getStardateFrom() > episodeTemplate.getStardateTo()) {
						float laterStardate = episodeTemplate.getStardateFrom();
						episodeTemplate.setStardateFrom(episodeTemplate.getStardateTo());
						episodeTemplate.setStardateTo(laterStardate);
					}
				}
			}
		}

		if (dateParts.size() == 2) {
			String datePartSecond = dateParts.get(1);
			List<PageLink> pageLinkList = wikitextApi.getPageLinksFromWikitext(datePartSecond);
			if (!pageLinkList.isEmpty()) {

				episodeTemplate.setYearFrom(parseYear(pageLinkList.get(0).getTitle(), true, title));

				if (pageLinkList.size() > 1) {
					episodeTemplate.setYearTo(parseYear(pageLinkList.get(1).getTitle(), false, title));
				}

				if (episodeTemplate.getYearTo() == null) {
					episodeTemplate.setYearTo(episodeTemplate.getYearFrom());
				}

				if (episodeTemplate.getYearFrom() != null && episodeTemplate.getYearTo() != null) {
					if (episodeTemplate.getYearFrom() > episodeTemplate.getYearTo()) {
						int laterDate = episodeTemplate.getYearFrom();
						episodeTemplate.setYearFrom(episodeTemplate.getYearTo());
						episodeTemplate.setYearTo(laterDate);
					}
				}

			} else {
				log.warn("Could not get any date links from {}", datePartSecond);
			}
		}
	}

	private Integer parseYear(String linkTitle, boolean from, String templateTitle) {
		try {
			Integer year = Integer.valueOf(linkTitle);
			if (year < 1000 || year > 9999) {
				String type =  from ? "\"from\"" : "\"to\"";
				log.warn("Tried to parse episode {} year {}, but it was out of range", type, year);
				return null;
			}

			return year;
		} catch (NumberFormatException e) {
			log.warn("Could not cast episode {} link {} to year", templateTitle, linkTitle);
			return null;
		}
	}

}
