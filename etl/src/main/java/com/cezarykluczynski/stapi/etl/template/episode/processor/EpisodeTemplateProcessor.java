package com.cezarykluczynski.stapi.etl.template.episode.processor;

import com.cezarykluczynski.stapi.etl.common.configuration.CommonTemplateConfiguration;
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ImageTemplateStardateYearEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYearCandidate;
import com.cezarykluczynski.stapi.etl.template.common.processor.ProductionSerialNumberProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DayMonthYearCandidateToLocalDateProcessor;
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
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
	private static final String N_AIRDATE_YEAR = "nairdateyear";
	private static final String S_AIRDATE_MONTH = "sairdatemonth";
	private static final String N_AIRDATE_DAY = "nairdateday";

	private DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessor;

	private ImageTemplateStardateYearEnrichingProcessor imageTemplateStardateYearEnrichingProcessor;

	private ProductionSerialNumberProcessor productionSerialNumberProcessor;

	@Inject
	public EpisodeTemplateProcessor(DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessor,
			@Qualifier(CommonTemplateConfiguration.EPISODE_TEMPALTE_STARDATE_YEAR_ENRICHING_PROCESSOR)
					ImageTemplateStardateYearEnrichingProcessor imageTemplateStardateYearEnrichingProcessor,
			ProductionSerialNumberProcessor productionSerialNumberProcessor) {
		this.dayMonthYearCandidateToLocalDateProcessor = dayMonthYearCandidateToLocalDateProcessor;
		this.imageTemplateStardateYearEnrichingProcessor = imageTemplateStardateYearEnrichingProcessor;
		this.productionSerialNumberProcessor = productionSerialNumberProcessor;
	}

	@Override
	public EpisodeTemplate process(Template item) throws Exception {
		EpisodeTemplate episodeTemplate = new EpisodeTemplate();
		episodeTemplate.setEpisodeStub(new Episode());

		String day = null;
		String month = null;
		String year = null;

		imageTemplateStardateYearEnrichingProcessor.enrich(EnrichablePair.of(item, episodeTemplate));

		for (Template.Part part : item.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			if (key == null) {
				continue;
			}

			switch (key) {
				case N_SEASON:
					episodeTemplate.setSeasonNumber(Integer.valueOf(value));
					break;
				case N_EPISODE:
					episodeTemplate.setEpisodeNumber(extractEpisodeNumber(value));
					break;
				case S_PRODUCTION_SERIAL_NUMBER:
					episodeTemplate.setProductionSerialNumber(productionSerialNumberProcessor.process(value));
					break;
				case B_FEATURE_LENGTH:
					episodeTemplate.setFeatureLength("1".equals(value));
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
				default:
					break;
			}
		}

		if (day != null && month != null && year != null) {
			episodeTemplate.setUsAirDate(dayMonthYearCandidateToLocalDateProcessor.process(DayMonthYearCandidate.of(day, month, year)));
		}

		return episodeTemplate;
	}

	private Integer extractEpisodeNumber(String episodeNumberCandidate) {
		List<String> numbers = Lists.newArrayList(episodeNumberCandidate.split("/"));

		try {
			return Integer.valueOf(numbers.get(0));
		} catch (Exception e) {
			log.error("Could not parse episode number {}", episodeNumberCandidate);
			return null;
		}
	}

}
