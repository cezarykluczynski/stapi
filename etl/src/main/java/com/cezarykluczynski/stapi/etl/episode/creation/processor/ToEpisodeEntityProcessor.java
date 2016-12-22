package com.cezarykluczynski.stapi.etl.episode.creation.processor;

import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate;
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ToEpisodeEntityProcessor implements ItemProcessor<EpisodeTemplate, Episode> {

	private GuidGenerator guidGenerator;

	private SeriesRepository seriesRepository;

	@Inject
	public ToEpisodeEntityProcessor(GuidGenerator guidGenerator, SeriesRepository seriesRepository) {
		this.guidGenerator = guidGenerator;
		this.seriesRepository = seriesRepository;
	}

	@Override
	public Episode process(EpisodeTemplate item) throws Exception {
		Episode episode = item.getEpisodeStub();

		episode.setTitle(item.getTitle());
		episode.setTitleGerman(item.getTitleGerman());
		episode.setTitleItalian(item.getTitleItalian());
		episode.setTitleJapanese(item.getTitleJapanese());
		episode.setPage(item.getPage());
		episode.setSeries(seriesRepository.findOne(item.getSeries().getId()));
		episode.setGuid(guidGenerator.generateFromPage(item.getPage(), Episode.class));
		episode.setSeasonNumber(item.getSeasonNumber());
		episode.setEpisodeNumber(item.getEpisodeNumber());
		episode.setProductionSerialNumber(item.getProductionSerialNumber());
		episode.setFeatureLength(item.getFeatureLength());
		episode.setStardateFrom(item.getStardateFrom());
		episode.setStardateTo(item.getStardateTo());
		episode.setYearFrom(item.getYearFrom());
		episode.setYearTo(item.getYearTo());
		episode.setUsAirDate(item.getUsAirDate());
		episode.setFinalScriptDate(item.getFinalScriptDate());

		return episode;
	}

}
