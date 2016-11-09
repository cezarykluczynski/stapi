package com.cezarykluczynski.stapi.etl.episode.creation.processor;

import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EpisodeWriter implements ItemWriter<Episode> {

	@Override
	public void write(List<? extends Episode> items) throws Exception {
		// TODO
	}

}
