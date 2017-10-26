package com.cezarykluczynski.stapi.etl.template.episode.processor;

import com.cezarykluczynski.stapi.etl.common.configuration.CommonTemplateConfiguration;
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ImageTemplateStardateYearEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYearCandidate;
import com.cezarykluczynski.stapi.etl.template.common.processor.ProductionSerialNumberProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DayMonthYearCandidateToLocalDateProcessor;
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate;
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplateParameter;
import com.cezarykluczynski.stapi.etl.util.constant.SeriesAbbreviation;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.season.repository.SeasonRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EpisodeTemplateProcessor implements ItemProcessor<Template, EpisodeTemplate> {

	private final DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessor;

	private final ImageTemplateStardateYearEnrichingProcessor imageTemplateStardateYearEnrichingProcessor;

	private final ProductionSerialNumberProcessor productionSerialNumberProcessor;

	private final SeasonRepository seasonRepository;

	public EpisodeTemplateProcessor(DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessor,
			@Qualifier(CommonTemplateConfiguration.EPISODE_TEMPALTE_STARDATE_YEAR_ENRICHING_PROCESSOR)
					ImageTemplateStardateYearEnrichingProcessor imageTemplateStardateYearEnrichingProcessor,
			ProductionSerialNumberProcessor productionSerialNumberProcessor, SeasonRepository seasonRepository) {
		this.dayMonthYearCandidateToLocalDateProcessor = dayMonthYearCandidateToLocalDateProcessor;
		this.imageTemplateStardateYearEnrichingProcessor = imageTemplateStardateYearEnrichingProcessor;
		this.productionSerialNumberProcessor = productionSerialNumberProcessor;
		this.seasonRepository = seasonRepository;
	}

	@Override
	public EpisodeTemplate process(Template item) throws Exception {
		EpisodeTemplate episodeTemplate = new EpisodeTemplate();
		episodeTemplate.setEpisodeStub(new Episode());

		String day = null;
		String month = null;
		String year = null;
		String seriesName = null;

		imageTemplateStardateYearEnrichingProcessor.enrich(EnrichablePair.of(item, episodeTemplate));

		for (Template.Part part : item.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			if (key == null) {
				continue;
			}

			switch (key) {
				case EpisodeTemplateParameter.S_SERIES:
					seriesName = value;
					break;
				case EpisodeTemplateParameter.N_SEASON:
					episodeTemplate.setSeasonNumber(Integer.valueOf(value));
					break;
				case EpisodeTemplateParameter.N_EPISODE:
					episodeTemplate.setEpisodeNumber(extractEpisodeNumber(value));
					break;
				case EpisodeTemplateParameter.S_PRODUCTION_SERIAL_NUMBER:
					episodeTemplate.setProductionSerialNumber(productionSerialNumberProcessor.process(value));
					break;
				case EpisodeTemplateParameter.B_FEATURE_LENGTH:
					episodeTemplate.setFeatureLength("1".equals(value));
					break;
				case EpisodeTemplateParameter.N_AIRDATE_DAY:
					day = value;
					break;
				case EpisodeTemplateParameter.S_AIRDATE_MONTH:
					month = value;
					break;
				case EpisodeTemplateParameter.N_AIRDATE_YEAR:
					year = value;
					break;
				default:
					break;
			}
		}

		if (episodeTemplate.getSeasonNumber() == 0 && SeriesAbbreviation.TOS.equals(seriesName)) {
			episodeTemplate.setSeasonNumber(1);
		}

		if (day != null && month != null && year != null) {
			episodeTemplate.setUsAirDate(dayMonthYearCandidateToLocalDateProcessor.process(DayMonthYearCandidate.of(day, month, year)));
		}

		if (seriesName != null && episodeTemplate.getSeasonNumber() != null) {
			episodeTemplate.setSeason(seasonRepository.findBySeriesAbbreviationAndSeasonNumber(seriesName, episodeTemplate.getSeasonNumber()));
		}

		Preconditions.checkNotNull(episodeTemplate.getSeason(), "Season has to be set in episode %s", episodeTemplate.getTitle());

		return episodeTemplate;
	}

	private Integer extractEpisodeNumber(String episodeNumberCandidate) {
		List<String> numbers = Lists.newArrayList(episodeNumberCandidate.split("/"));

		try {
			return Integer.valueOf(numbers.get(0));
		} catch (Exception e) {
			log.info("Could not parse episode number {}", episodeNumberCandidate);
			return null;
		}
	}

}
