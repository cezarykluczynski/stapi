package com.cezarykluczynski.stapi.etl.template.episode.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.episode.creation.dto.ModuleEpisodeData;
import com.cezarykluczynski.stapi.etl.episode.creation.service.ModuleEpisodeDataProvider;
import com.cezarykluczynski.stapi.etl.episode.creation.service.SeriesToEpisodeBindingService;
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate;
import com.cezarykluczynski.stapi.etl.util.constant.SeriesAbbreviation;
import com.cezarykluczynski.stapi.model.season.repository.SeasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ModuleEpisodeDataEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<ModuleEpisodeData, EpisodeTemplate>> {

	private final ModuleEpisodeDataProvider moduleEpisodeDataProvider;

	private final SeasonRepository seasonRepository;

	private final SeriesToEpisodeBindingService seriesToEpisodeBindingService;

	@Override
	public void enrich(EnrichablePair<ModuleEpisodeData, EpisodeTemplate> enrichablePair) throws Exception {
		EpisodeTemplate episodeTemplate = enrichablePair.getOutput();
		ModuleEpisodeData moduleEpisodeData = enrichablePair.getInput();
		if (moduleEpisodeData != null) {
			if (moduleEpisodeData.getSeries() != null) {
				episodeTemplate.setSeries(seriesToEpisodeBindingService.mapAbbreviationToSeries(moduleEpisodeData.getSeries()));
			}
			if (moduleEpisodeData.getSeasonNumber() != null) {
				episodeTemplate.setSeasonNumber(moduleEpisodeData.getSeasonNumber());
			}
			if (moduleEpisodeData.getSeries() != null && moduleEpisodeData.getSeasonNumber() != null) {
				if (SeriesAbbreviation.TOS.equals(moduleEpisodeData.getSeries()) && moduleEpisodeData.getSeasonNumber() == 0) {
					episodeTemplate.setSeason(seasonRepository.findBySeriesAbbreviationAndSeasonNumber(moduleEpisodeData.getSeries(), 1));
					episodeTemplate.setEpisodeNumber(0); // there is no entity for season 0 of TOS, so let's make it episode 0 of season 1 instead
				} else {
					episodeTemplate.setSeason(seasonRepository.findBySeriesAbbreviationAndSeasonNumber(moduleEpisodeData.getSeries(),
							episodeTemplate.getSeasonNumber()));
				}
			}
			if (moduleEpisodeData.getEpisodeNumber() != null) {
				episodeTemplate.setEpisodeNumber(moduleEpisodeData.getEpisodeNumber());
			}
			if (moduleEpisodeData.getReleaseDay() != null
					&& moduleEpisodeData.getReleaseMonth() != null
					&& moduleEpisodeData.getReleaseYear() != null) {
				episodeTemplate.setUsAirDate(LocalDate.of(moduleEpisodeData.getReleaseYear(), moduleEpisodeData.getReleaseMonth(),
						moduleEpisodeData.getReleaseDay()));
			}
			if (moduleEpisodeData.getProductionNumber() != null) {
				episodeTemplate.setProductionSerialNumber(moduleEpisodeData.getProductionNumber());
			}
		}
	}

}
