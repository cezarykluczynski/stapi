package com.cezarykluczynski.stapi.etl.episode.creation.processor;

import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.episode.repository.EpisodeRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class EpisodeWriter implements ItemWriter<Episode> {

	private final EpisodeRepository episodeRepository;

	public EpisodeWriter(EpisodeRepository episodeRepository) {
		this.episodeRepository = episodeRepository;
	}

	@Override
	public void write(Chunk<? extends Episode> items) throws Exception {
		episodeRepository.saveAll(items.getItems());
	}

}
