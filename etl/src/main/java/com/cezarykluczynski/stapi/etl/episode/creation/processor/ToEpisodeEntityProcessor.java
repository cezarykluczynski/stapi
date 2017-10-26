package com.cezarykluczynski.stapi.etl.episode.creation.processor;

import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class ToEpisodeEntityProcessor implements ItemProcessor<EpisodeTemplate, Episode> {

	private final UidGenerator uidGenerator;

	private final SeriesRepository seriesRepository;

	public ToEpisodeEntityProcessor(UidGenerator uidGenerator, SeriesRepository seriesRepository) {
		this.uidGenerator = uidGenerator;
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
		episode.setSeason(item.getSeason());
		episode.setUid(uidGenerator.generateFromPage(item.getPage(), Episode.class));
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
