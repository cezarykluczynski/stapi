package com.cezarykluczynski.stapi.etl.template.episode.processor;

import com.cezarykluczynski.stapi.etl.template.common.dto.DayMonthYearCandidate;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DayMonthYearProcessor;
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
@Slf4j
public class EpisodeTemplateProcessor implements ItemProcessor<Template, EpisodeTemplate> {

	private static final String N_SEASON = "nseason";
	private static final String N_EPISODE = "nepisode";
	private static final String S_PRODUCTION_SERIAL_NUMBER = "sproductionserialnumber";
	private static final String B_FEATURE_LENGTH = "bfeaturelength";
	private static final String WS_DATE = "wsdate";
	private static final String N_AIRDATE_YEAR = "nairdateyear";
	private static final String S_AIRDATE_MONTH = "sairdatemonth";
	private static final String N_AIRDATE_DAY = "nairdateday";

	private WikitextApi wikitextApi;

	private DayMonthYearProcessor dayMonthYearProcessor;

	@Inject
	public EpisodeTemplateProcessor(WikitextApi wikitextApi, DayMonthYearProcessor dayMonthYearProcessor) {
		this.wikitextApi = wikitextApi;
		this.dayMonthYearProcessor = dayMonthYearProcessor;
	}

	@Override
	public EpisodeTemplate process(Template item) throws Exception {
		EpisodeTemplate episodeTemplate = new EpisodeTemplate();
		episodeTemplate.setEpisodeStub(new Episode());

		String day = null;
		String month = null;
		String year = null;

		for (Template.Part part : item.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case N_SEASON:
					episodeTemplate.setSeasonNumber(Integer.valueOf(value));
					break;
				case N_EPISODE:
					episodeTemplate.setEpisodeNumber(getEpisodeNumber(value));
					break;
				case S_PRODUCTION_SERIAL_NUMBER:
					episodeTemplate.setProductionSerialNumber(extractProductionSerialNumber(value));
					break;
				case B_FEATURE_LENGTH:
					episodeTemplate.setFeatureLength("1".equals(value));
					break;
				case WS_DATE:
					setDates(episodeTemplate, value);
					break;
				case N_AIRDATE_DAY:
					day = value;
					break;
				case S_AIRDATE_MONTH:
					month = value;
					break;
				case N_AIRDATE_YEAR:
					year = value;
					break;
			}
		}

		if (day != null && month != null && year != null) {
			episodeTemplate.setUsAirDate(dayMonthYearProcessor.process(DayMonthYearCandidate.of(day, month, year)));
		}

		return episodeTemplate;
	}

	private String extractProductionSerialNumber(String value) {
		if (value != null && value.length() < 20) {
			return value;
		} else {
			try {
				JSONObject jsonObject = new JSONObject(value);
				return jsonObject.has("content") ? (String) jsonObject.get("content") : null;
			} catch (JSONException e) {
				log.error("Could not parse production serial number {} as JSON, and it is too long", value);
				return null;
			}
		}
	}

	private Integer getEpisodeNumber(String episodeNumberCandidate) {
		List<String> numbers = Lists.newArrayList(episodeNumberCandidate.split("/"));

		try {
			return Integer.valueOf(numbers.get(0));
		} catch (Exception e) {
			log.error("Could not parse episode number {}", episodeNumberCandidate);
			return null;
		}
	}

	private void setDates(EpisodeTemplate episodeTemplate, String value) {
		if (value == null) {
			return;
		}

		List<String> dateParts = Lists.newArrayList(value.split("\\s"));

		if (dateParts.size() == 2) {
			try {
				episodeTemplate.setStardate(Float.valueOf(dateParts.get(0)));
			} catch (NumberFormatException e) {
				log.warn("Could not cast episode stardate {} to float", dateParts.get(0));
			}

			String datePartSecond = dateParts.get(1);
			List<PageLink> pageLinkList = wikitextApi.getPageLinksFromWikitext(datePartSecond);
			if (!pageLinkList.isEmpty()) {
				String title = pageLinkList.get(0).getTitle();
				try {
					Integer year = Integer.valueOf(title);
					if (year < 1000 || year > 9999) {
						log.warn("Tried to parse episode year {}, but it was out of range", year);
						return;
					}

					episodeTemplate.setYear(year);
				} catch (NumberFormatException e) {
					log.warn("Could not cast episode stardate {} to year", title);
				}
			} else {
				log.warn("Could not get any date links from {}", datePartSecond);
			}
		}
	}

}
