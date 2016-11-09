package com.cezarykluczynski.stapi.etl.episode.creation.processor;

import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class ToEpisodeEntityProcessor implements ItemProcessor<EpisodeTemplate, Episode> {

	@Override
	public Episode process(EpisodeTemplate item) throws Exception {
		// TODO
		return null;
	}

}
